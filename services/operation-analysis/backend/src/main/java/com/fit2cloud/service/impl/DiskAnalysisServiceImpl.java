package com.fit2cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fit2cloud.base.entity.CloudAccount;
import com.fit2cloud.base.entity.VmCloudDisk;
import com.fit2cloud.base.entity.VmCloudServer;
import com.fit2cloud.base.mapper.BaseVmCloudDiskMapper;
import com.fit2cloud.base.service.IBaseCloudAccountService;
import com.fit2cloud.common.utils.CurrentUserUtils;
import com.fit2cloud.common.utils.PageUtil;
import com.fit2cloud.constants.DiskTypeConstants;
import com.fit2cloud.constants.SpecialAttributesConstants;
import com.fit2cloud.controller.request.disk.PageDiskRequest;
import com.fit2cloud.controller.request.disk.ResourceAnalysisRequest;
import com.fit2cloud.controller.response.BarTreeChartData;
import com.fit2cloud.controller.response.ChartData;
import com.fit2cloud.dao.entity.OrgWorkspace;
import com.fit2cloud.dao.mapper.OrgWorkspaceMapper;
import com.fit2cloud.dto.AnalysisDiskDTO;
import com.fit2cloud.dto.KeyValue;
import com.fit2cloud.service.IDiskAnalysisService;
import com.fit2cloud.service.IPermissionService;
import com.fit2cloud.service.IServerAnalysisService;
import com.fit2cloud.utils.OperationUtils;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.list.TreeList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author jianneng
 * @date 2022/12/24 11:29
 **/
@Service
public class DiskAnalysisServiceImpl implements IDiskAnalysisService {

    @Resource
    private BaseVmCloudDiskMapper baseVmCloudDiskMapper;
    @Resource
    private IBaseCloudAccountService iBaseCloudAccountService;
    @Resource
    private IServerAnalysisService iServerAnalysisService;
    @Resource
    private IPermissionService permissionService;
    @Resource
    private OrgWorkspaceMapper orgWorkspaceMapper;

    /**
     * 分页查询磁盘明细
     * @param request 分页查询磁盘参数
     */
    @Override
    public IPage<AnalysisDiskDTO> pageDisk(PageDiskRequest request) {
        Page<AnalysisDiskDTO> page = PageUtil.of(request, AnalysisDiskDTO.class, null,  true);
        // 构建查询参数
        MPJLambdaWrapper<VmCloudDisk> wrapper = addDiskPageQuery(request);
        return baseVmCloudDiskMapper.selectJoinPage(page,AnalysisDiskDTO.class, wrapper);
    }

    /**
     * 分页查询参数
     */
    private MPJLambdaWrapper<VmCloudDisk> addDiskPageQuery(PageDiskRequest request) {
        List<String> sourceIds = permissionService.getSourceIds();
        if (!CurrentUserUtils.isAdmin() && CollectionUtils.isNotEmpty(sourceIds)) {
            request.setSourceIds(sourceIds);
        }
        MPJLambdaWrapper<VmCloudDisk> wrapper = defaultDiskQuery(new MPJLambdaWrapper<>(),request.getAccountIds());
        wrapper.orderByDesc(VmCloudDisk::getCreateTime);
        wrapper.selectAs(CloudAccount::getName, AnalysisDiskDTO::getAccountName);
        wrapper.selectAs(CloudAccount::getPlatform,AnalysisDiskDTO::getPlatform);
        wrapper.selectAs(VmCloudServer::getInstanceName, AnalysisDiskDTO::getVmInstanceName);
        wrapper.eq(VmCloudDisk::getAccountId,VmCloudServer::getAccountId);
        wrapper.like(StringUtils.isNotBlank(request.getName()), VmCloudDisk::getDiskName, request.getName());
        wrapper.in(CollectionUtils.isNotEmpty(request.getSourceIds()), VmCloudDisk::getSourceId, request.getSourceIds());
        wrapper.leftJoin(VmCloudServer.class,VmCloudServer::getInstanceUuid, VmCloudDisk::getInstanceUuid);
        return wrapper;
    }

    /**
     * 查询磁盘默认参数
     * 云账号、排除已删除状态的
     */
    private MPJLambdaWrapper<VmCloudDisk> defaultDiskQuery(MPJLambdaWrapper<VmCloudDisk> wrapper,List<String> accountIds){
        wrapper.selectAll(VmCloudDisk.class);
        wrapper.in(CollectionUtils.isNotEmpty(accountIds), VmCloudDisk::getAccountId, accountIds);
        wrapper.leftJoin(CloudAccount.class,CloudAccount::getId, VmCloudDisk::getAccountId);
        wrapper.notIn(true, VmCloudDisk::getStatus, List.of(SpecialAttributesConstants.StatusField.DISK_DELETE));
        return wrapper;
    }

    /**
     * 获取所有云账号
     */
    @Override
    public List<CloudAccount> getAllCloudAccount() {
        QueryWrapper<CloudAccount> queryWrapper = new QueryWrapper<>();
        return iBaseCloudAccountService.list(queryWrapper);
    }

    /**
     * 磁盘分析参数
     * @param request 磁盘分析参数
     */
    private MPJLambdaWrapper<VmCloudDisk> addDiskAnalysisQuery(ResourceAnalysisRequest request) {
        List<String> sourceIds = permissionService.getSourceIds();
        if (!CurrentUserUtils.isAdmin() && CollectionUtils.isNotEmpty(sourceIds)) {
            request.setSourceIds(sourceIds);
        }
        MPJLambdaWrapper<VmCloudDisk> wrapper = defaultDiskQuery(new MPJLambdaWrapper<>(),request.getAccountIds());
        wrapper.selectAs(CloudAccount::getName, AnalysisDiskDTO::getAccountName);
        wrapper.selectAs(CloudAccount::getPlatform,AnalysisDiskDTO::getPlatform);
        wrapper.in(CollectionUtils.isNotEmpty(request.getSourceIds()), VmCloudDisk::getSourceId, request.getSourceIds());
        return wrapper;
    }

    /**
     * 磁盘分布查询
     * 包括按云账号、状态、类型分布
     */
    @Override
    public Map<String, List<KeyValue>> spread(ResourceAnalysisRequest request) {
        Map<String,List<KeyValue>> result = new HashMap<>(1);
        Map<String,CloudAccount> accountMap = iServerAnalysisService.getAllAccountIdMap();
        if(accountMap.size()==0){
            return result;
        }
        MPJLambdaWrapper<VmCloudDisk> queryWrapper = addDiskAnalysisQuery(request);
        List<AnalysisDiskDTO> diskList = baseVmCloudDiskMapper.selectJoinList(AnalysisDiskDTO.class, queryWrapper);
        //把除空闲与挂载状态设置为其他状态
        diskList = diskList.stream().filter(v->accountMap.containsKey(v.getAccountId())).toList();
        diskList.forEach(v->{
            if(!StringUtils.equalsIgnoreCase(v.getStatus(),SpecialAttributesConstants.StatusField.AVAILABLE) && !StringUtils.equalsIgnoreCase(v.getStatus(),SpecialAttributesConstants.StatusField.IN_USE)){
                v.setStatus(SpecialAttributesConstants.StatusField.OTHER);
            }
        });
        diskList = diskList.stream().filter(v->StringUtils.isNotEmpty(v.getAccountId())).toList();
        Map<String,String> statusMap = new HashMap<>(3);
        statusMap.put(SpecialAttributesConstants.StatusField.AVAILABLE,"空闲");
        statusMap.put(SpecialAttributesConstants.StatusField.IN_USE,"已挂载");
        statusMap.put(SpecialAttributesConstants.StatusField.OTHER,"其他");
        if(request.isStatisticalBlock()){
            Map<String,Long> byAccountMap = diskList.stream().collect(Collectors.groupingBy(AnalysisDiskDTO::getAccountId, Collectors.counting()));
            result.put("byAccount",byAccountMap.entrySet().stream().map(c -> new KeyValue(StringUtils.isEmpty(accountMap.get(c.getKey()).getName())?c.getKey():accountMap.get(c.getKey()).getName(), c.getValue())).toList());
            Map<String,Long> byStatusMap = diskList.stream().collect(Collectors.groupingBy(AnalysisDiskDTO::getStatus, Collectors.counting()));
            result.put("byStatus",byStatusMap.entrySet().stream().map(c -> new KeyValue(statusMap.get(c.getKey()), c.getValue())).toList());
            Map<String,Long> byTypeMap = diskList.stream().collect(Collectors.groupingBy(AnalysisDiskDTO::getDiskType, Collectors.counting()));
            result.put("byType",byTypeMap.entrySet().stream().map(c -> new KeyValue(DiskTypeConstants.getName(c.getKey()), c.getValue())).toList());
        }else{
            Map<String,LongSummaryStatistics> byAccountMap = diskList.stream().filter(v->StringUtils.isNotEmpty(v.getAccountId())).collect(Collectors.groupingBy(AnalysisDiskDTO::getAccountId, Collectors.summarizingLong(AnalysisDiskDTO::getSize)));
            result.put("byAccount",byAccountMap.entrySet().stream().map(c -> new KeyValue(StringUtils.isEmpty(accountMap.get(c.getKey()).getName())?c.getKey():accountMap.get(c.getKey()).getName(), c.getValue().getSum())).toList());
            Map<String,LongSummaryStatistics> byStatusMap = diskList.stream().collect(Collectors.groupingBy(AnalysisDiskDTO::getStatus, Collectors.summarizingLong(AnalysisDiskDTO::getSize)));
            result.put("byStatus",byStatusMap.entrySet().stream().map(c -> new KeyValue(statusMap.get(c.getKey()), c.getValue().getSum())).toList());
            Map<String,LongSummaryStatistics> byTypeMap = diskList.stream().collect(Collectors.groupingBy(AnalysisDiskDTO::getDiskType, Collectors.summarizingLong(AnalysisDiskDTO::getSize)));
            result.put("byType",byTypeMap.entrySet().stream().map(c -> new KeyValue(DiskTypeConstants.getName(c.getKey()), c.getValue().getSum())).toList());
        }
        return result;
    }

    /**
     * 磁盘趋势
     * 统计一段时间内所有新增的磁盘数量生产趋势数据
     */
    @Override
    public List<ChartData> diskIncreaseTrend(ResourceAnalysisRequest request) {
        List<ChartData> tempChartDataList = new ArrayList<>();
        List<CloudAccount> accountList = getAllCloudAccount();
        Map<String,CloudAccount> accountMap = accountList.stream().collect(Collectors.toMap(CloudAccount::getId,v->v,(k1,k2)->k1));
        if(accountMap.size()==0){
            return tempChartDataList;
        }
        MPJLambdaWrapper<VmCloudDisk>  queryWrapper = addDiskAnalysisQuery(request);
        List<AnalysisDiskDTO> diskList = baseVmCloudDiskMapper.selectJoinList(AnalysisDiskDTO.class, queryWrapper);
        if(CollectionUtils.isNotEmpty(diskList)){
            //格式化创建时间,删除时间
            diskList = diskList.stream().filter(v->accountMap.containsKey(v.getAccountId())).filter(v->Objects.nonNull(v.getCreateTime())).toList();
            diskList.forEach(v->{
                v.setCreateMonth(v.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                if(Objects.nonNull(v.getUpdateTime()) && StringUtils.equalsIgnoreCase(v.getStatus(),SpecialAttributesConstants.StatusField.DISK_DELETE)){
                    v.setDeleteMonth(v.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
            });
            Map<String,List<AnalysisDiskDTO>> accountGroup = diskList.stream().filter(v->StringUtils.isNotEmpty(v.getAccountId())).collect(Collectors.groupingBy(AnalysisDiskDTO::getAccountId));
            setDiskIncreaseTrendData(accountGroup, request.isStatisticalBlock(), request.getDayNumber(), tempChartDataList);
        }
        return tempChartDataList;
    }

    private void setDiskIncreaseTrendData(Map<String,List<AnalysisDiskDTO>> accountGroup,boolean isStatisticalBlock,Long days,List<ChartData> tempChartDataList){
        List<String> dateRangeList = iServerAnalysisService.getRangeDateStrList(days);
        for (Map.Entry<String,List<AnalysisDiskDTO>> entry : accountGroup.entrySet()) {
            String accountId = entry.getKey();
            Map<String, Long> month;
            Map<String, Long> delMonth;
            //容量
            if (!isStatisticalBlock) {
                month = accountGroup.get(accountId).stream().collect(Collectors.groupingBy(AnalysisDiskDTO::getCreateMonth, Collectors.summingLong(AnalysisDiskDTO::getSize)));
                delMonth = accountGroup.get(accountId).stream().filter(v -> StringUtils.equalsIgnoreCase(v.getStatus(), SpecialAttributesConstants.StatusField.DISK_DELETE) && StringUtils.isNotEmpty(v.getDeleteMonth())).collect(Collectors.groupingBy(AnalysisDiskDTO::getDeleteMonth, Collectors.summingLong(AnalysisDiskDTO::getSize)));
            } else {
                month = accountGroup.get(accountId).stream().collect(Collectors.groupingBy(AnalysisDiskDTO::getCreateMonth, Collectors.counting()));
                delMonth = accountGroup.get(accountId).stream().filter(v -> StringUtils.equalsIgnoreCase(v.getStatus(), SpecialAttributesConstants.StatusField.DISK_DELETE) && StringUtils.isNotEmpty(v.getDeleteMonth())).collect(Collectors.groupingBy(AnalysisDiskDTO::getDeleteMonth, Collectors.counting()));
            }
            Map<String, Long> finalMonth = month;
            Map<String, Long> finalDelMonth = delMonth;
            dateRangeList.forEach(dateStr -> {
                ChartData chartData = new ChartData();
                chartData.setXAxis(dateStr);
                chartData.setGroupName(accountGroup.get(accountId).get(0).getAccountName());
                chartData.setYAxis(new BigDecimal(iServerAnalysisService.getResourceTotalDateBefore(finalMonth, dateStr) - iServerAnalysisService.getResourceTotalDateBefore(finalDelMonth, dateStr)));
                tempChartDataList.add(chartData);
            });
        }
    }

    /**
     * 磁盘在组织工作空间上的分布
     */
    @Override
    public Map<String,List<BarTreeChartData>> analysisCloudDiskByOrgWorkspace(ResourceAnalysisRequest request){
        Map<String,List<BarTreeChartData>> result = new HashMap<>(2);
        List<BarTreeChartData> workspaceList =  workspaceSpread(request);
        if(request.isAnalysisWorkspace()){
            result.put("tree",workspaceList);
            return result;
        }else{
            orgSpread(request,workspaceList,result);
        }
        return result;
    }

    /**
     * 组织上的分布
     */
    private void orgSpread(ResourceAnalysisRequest request, List<BarTreeChartData> workspaceList, Map<String,List<BarTreeChartData>> result){
        //组织下工作空间添加标识
        workspaceList.forEach(v-> v.setName(v.getName()+"(工作空间)"));
        //工作空间按组织ID分组
        Map<String,List<BarTreeChartData>> workspaceMap = workspaceList.stream().collect(Collectors.groupingBy(BarTreeChartData::getPId));
        //查询所有组织初始化为chart数据
        List<BarTreeChartData> orgList = iServerAnalysisService.initOrgChartData();
        MPJLambdaWrapper<OrgWorkspace> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(OrgWorkspace.class);
        wrapper.in(CollectionUtils.isNotEmpty(request.getAccountIds()),VmCloudDisk::getAccountId,request.getAccountIds());
        //查询所有有授权磁盘的组织
        OperationUtils.initOrgWorkspaceAnalysisData(orgList, getSpreadForType(request.isStatisticalBlock(), "org" , wrapper));
        //初始化子级
        orgList.forEach(OperationUtils::setSelfToChildren);
        //扁平数据
        result.put("all",orgList);
        //树结构
        List<BarTreeChartData> chartDataList = new TreeList<>();
        orgList.stream().filter(o->StringUtils.isEmpty(o.getPId())).forEach(v->{
            v.setGroupName("org");
            v.setName(v.getName()+"(组织)");
            v.getChildren().addAll(iServerAnalysisService.getChildren(v,orgList,workspaceMap));
            chartDataList.add(v);
        });
        //组织管理员的话，只有一个跟节点，然后只返回他的子集
        if (CurrentUserUtils.isOrgAdmin()) {
            result.put("tree",chartDataList.get(0).getChildren().stream().filter(v->v.getValue()>0).toList());
        }else{
            result.put("tree",chartDataList.stream().filter(v->v.getValue()>0).toList());
        }
    }

    /**
     * 工作空间上分布
     * @param request 磁盘分析参数
     * @return List<BarTreeChartData>
     */
    private List<BarTreeChartData> workspaceSpread(ResourceAnalysisRequest request){
        MPJLambdaWrapper<OrgWorkspace> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(OrgWorkspace.class);
        wrapper.in(CollectionUtils.isNotEmpty(request.getAccountIds()),VmCloudDisk::getAccountId,request.getAccountIds());
        List<BarTreeChartData> workspaceList = iServerAnalysisService.initWorkspaceChartData();
        OperationUtils.initOrgWorkspaceAnalysisData(workspaceList, getSpreadForType(request.isStatisticalBlock(),"workspace",wrapper));
        return workspaceList.stream().filter(v->v.getValue()!=0).toList();
    }

    private List<BarTreeChartData> getSpreadForType(boolean isBlock, String type, MPJLambdaWrapper<OrgWorkspace> wrapper){
        List<String> sourceIds = permissionService.getSourceIds();
        wrapper.in(!CurrentUserUtils.isAdmin() && CollectionUtils.isNotEmpty(sourceIds),OrgWorkspace::getId,sourceIds);
        wrapper.eq(OrgWorkspace::getType,type);
        wrapper.notIn(true, VmCloudDisk::getStatus, List.of("deleted"));
        wrapper.leftJoin(VmCloudDisk.class,VmCloudDisk::getSourceId,OrgWorkspace::getId);
        wrapper.groupBy(OrgWorkspace::getId,OrgWorkspace::getName);
        List<BarTreeChartData> list;
        if (isBlock){
            wrapper.selectCount(VmCloudDisk::getId,BarTreeChartData::getValue);
        }else{
            wrapper.selectCount(VmCloudDisk::getSize,BarTreeChartData::getValue);
        }
        list = orgWorkspaceMapper.selectJoinList(BarTreeChartData.class,wrapper);
        return list;
    }


}