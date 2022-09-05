package com.fit2cloud.base.service.impl;

import com.fit2cloud.base.entity.Organization;
import com.fit2cloud.base.entity.Workspace;
import com.fit2cloud.base.mapper.BaseOrganizationMapper;
import com.fit2cloud.base.service.IBaseOrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fit2cloud.base.service.IBaseWorkspaceService;
import com.fit2cloud.common.utils.OrganizationUtil;
import com.fit2cloud.response.OrganizationTree;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fit2cloud
 * @since
 */
@Service
public class BaseOrganizationServiceImpl extends ServiceImpl<BaseOrganizationMapper, Organization> implements IBaseOrganizationService {
    private final String TreeTypeOrganization = "ORGANIZATION";
    private final String TreeTypeOrganizationAndWorkspace = "ORGANIZATION_AND_WORKSPACE";
    @Resource
    private IBaseWorkspaceService workspaceService;

    @Override
    public List<OrganizationTree> tree() {
        return OrganizationUtil.toTree(list());
    }

    @Override
    public List<OrganizationTree> tree(String type) {
        List<OrganizationTree> organizationTree = tree();
        if (StringUtils.isEmpty(type) || StringUtils.equals(type, TreeTypeOrganization)) {
            return organizationTree;
        }
        // 查询到所有的工作空间
        List<Workspace> workspaces = workspaceService.list();
        if (StringUtils.equals(type, TreeTypeOrganizationAndWorkspace)) {
            appendOrganizationTree(organizationTree, workspaces);
        }
        return organizationTree;
    }

    private void appendOrganizationTree(List<OrganizationTree> organizationTree, List<Workspace> workspaces) {
        for (OrganizationTree tree : organizationTree) {
            List<Workspace> childWorkspaces = workspaces.stream().filter(workspace -> StringUtils.equals(tree.getId(), workspace.getOrganizationId())).toList();
            tree.setWorkspaces(childWorkspaces);
            if (CollectionUtils.isNotEmpty(tree.getChildren())) {
                appendOrganizationTree(tree.getChildren(), workspaces);
            }
        }
    }
}