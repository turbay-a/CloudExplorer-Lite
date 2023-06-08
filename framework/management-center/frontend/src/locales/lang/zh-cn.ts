const message = {
  user: {
    user: "用户",
    type: "用户角色",
    username: "用户名",
    name: "显示名称",
    email: "邮箱",
    phone: "手机号码",
    wechatAccount: "企业微信账号",
    role: "角色",
    password: "密码",
    local: "本地创建",
    extra: "第三方",
    source: "来源",
    status: "状态",
    manage: "用户管理",
    delete_role: "删除角色",
    add_role: "添加角色",
    set_role: "设置角色",
    has_role: "拥有角色",
    add_org: "组织",
    add_workspace: "工作空间",
    delete_confirm: "确认删除用户",
    delete_role_confirm: "确认删除用户角色",
    notify_setting: "通知设置",
    notify_tips:
      "邮箱、手机号设置后将与用户基本信息关联。手机号将做为钉钉平台推送标识。企业微信账号参考",
    validate: {
      phone_format: "手机号码格式错误",
      email_format: "邮箱格式错误",
      selected: "请选择用户",
      role_empty: "角色信息不能为空",
      param: "参数不合法",
      user_type_empty: "用户角色不能为空",
      org: "请选择组织",
      workspace: "请选择工作空间",
    },
  },
  workspace: {
    user_count: "用户数",
    workspace_name: "工作空间名称",
    org: "父级组织",
    validate: {
      repeat: "{0}重复",
    },
  },
  // 云账号相关国际化
  cloud_account: {
    name: "云账号名称",
    name_placeholder: "请输入云账号名称",
    base_setting: "基本设置",
    sync_setting: "同步设置",
    cloud_account_size: "云账号必须选择一条",
    verification: "连接校验",
    sync_message: "同步",
    edit_job_message: "数据同步设置",
    platform: "云平台",
    native_state_valid_message: "云账号有效",
    native_state_invalid_message: "云账号无效",
    native_state: "状态",
    native_state_valid: "有效",
    native_state_invalid: "无效",
    native_sync_status: "同步状态",
    native_sync: {
      init: "初始化",
      success: "同步成功",
      failed: "同步失败",
      syncing: "同步中",
      unknown: "未知",
    },
    last_sync_time: "最近同步时间",
    please_select_platform_message: "请选择云平台",
    account_information_message: "账号信息",
    field_is_not_null: "字段不能为空",
    name_is_not_empty: "云账号名称不能为空",
    platform_is_not_empty: "云平台不能为空",
    balance: {
      money: "账户余额",
      unit: "元",
    },
    resource: "我的资源",
    sync: {
      sync: "同步",
      syncBill: "同步账单",
      syncResource: "同步资源",
      synchronizing: "同步中",
      unit: "个",
      detail: "详情",
      noDetail: "没有同步信息",
      start: "开始同步",
      end: "结束同步",
      area: "数据中心/区域",
      finishArea: "已同步数据中心/区域",
      record: "同步记录",
      time: "同步时间",
      status: "同步状态",
      resource: "同步资源",
      setting: "数据同步设置",
      once: "同步一次",
      region: "区域",
      range: "同步范围",
      timing: "同步频率",
      interval: "每隔",
      interval_time_unit: {
        millisecond: "毫秒",
        second: "秒",
        minute: "分钟",
        hour: "小时",
        day: "天",
      },
    },
  },
  // 组织相关国际化
  org_manage: {
    affiliated_organization: "所属组织",
    organization_name_is_not_empty: "组织名称不能为空",
    organization_description_is_not_empty: "组织描述不能为空",
  },
  log_manage: {
    login: "登录日志",
    vm: "云主机操作日志",
    disk: "磁盘操作日志",
    platform: "平台管理日志",
    operator: "操作人",
    module: "模块",
    menu: "菜单",
    type: "操作类型",
    resource: "操作对象",
    ip: "操作IP",
    status: "操作状态",
    view_details: "查看详情",
    belong_vm: "所属云主机",
    content: "操作详情",
    login_time: "登录时间",
    op_time: "操作时间",
    btn: {
      clear_policy: "清空策略",
    },
  },
  system_setting: {
    params_setting: {
      recycle_bin: {
        strategy: "回收站策略",
        open: "开启回收站",
        recycle_strategy: "回收站策略",
        tips: "注意:",
        tips_1:
          "开启回收站功能后，删除云主机、云磁盘资源时会将所删资源放入回收站，请到回收站中彻底删除。",
        tips_2:
          "关闭回收站功能后，用户删除云主机、云磁盘等资源时会立即删除无法恢复。",
      },
    },
  },
};
export default {
  ...message,
};
