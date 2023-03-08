const message = {
  user: {
    type: "用戶類型",
    name: "姓名",
    email: "郵箱",
    phone: "手機號碼",
    wechatAccount: "企業微信賬號",
    role: "角色",
    password: "密碼",
    local: "本地創建",
    extra: "第三方",
    source: "來源",
    status: "狀態",
    manage: "用戶管理",
    delete_role: "刪除角色",
    add_role: "添加角色",
    set_role: "設置角色",
    has_role: "擁有角色",
    add_org: "組織",
    add_workspace: "工作空間",
    delete_confirm: "確認刪除用戶",
    notify_setting: "通知設置",
    notify_tips:
      "郵箱、手機號設置後將與用戶基本信息關聯。手機號將做為釘釘平臺推送標識。企業微信賬號參考",
    validate: {
      phone_format: "手機號碼格式錯誤",
      email_format: "郵箱格式錯誤",
      selected: "請選擇用戶",
      role_empty: "角色信息不能為空",
      param: "參數不合法",
      user_type_empty: "用戶類型不能為空",
      org: "請選擇組織",
      workspace: "請選擇工作空間",
    },
  },
  workspace: {
    user_count: "用戶數",
    workspace_name: "工作空間名稱",
    org: "父級組織",
    validate: {
      repeat: "{0}重複",
    },
  },
  // 雲賬號相關國際化
  cloud_account: {
    syncBill: "同步帳單",
    syncResource: "同步資源",
    name: "雲賬號名稱",
    name_placeholder: "請輸入雲賬號名稱",
    base_setting: "基本設置",
    sync_setting: "同步設置",
    cloud_account_size: "雲賬號必須選擇一條",
    verification: "校驗",
    sync_message: "同步",
    edit_job_message: "編輯定時任務",
    platform: "雲平臺",
    native_state_valid_message: "雲賬號有效",
    native_state_invalid_message: "雲賬號無效",
    native_state: "雲賬號狀態",
    native_state_valid: "有效",
    native_state_invalid: "無效",
    native_sync_status: "雲賬號同步狀態",
    native_sync: {
      init: "初始化",
      success: "成功",
      failed: "失敗",
      syncing: "同步中",
      unknown: "未知",
    },
    last_sync_time: "最近同步時間",
    please_select_platform_message: "請選擇雲平臺",
    account_information_message: "賬號信息",
    field_is_not_null: "字段不能為空",
    name_is_not_empty: "雲賬號名稱不能為空",
    platform_is_not_empty: "雲平臺不能為空",
    balance: {
      money: "賬戶余額",
      unit: "元",
    },
    resource: "我的資源",
    sync: {
      synchronizing: "同步中",
      unit: "個",
      detail: "詳情",
      noDetail: "沒有同步信息",
      start: "開始同步",
      end: "結束同步",
      area: "數據中心/區域",
      finishArea: "已同步數據中心/區域",
      record: "同步記錄",
      time: "同步時間",
      status: "同步狀態",
      resource: "同步資源",
      setting: "定時同步設置",
      once: "同步一次",
      region: "區域",
      range: "同步範圍",
      timing: "資源同步頻率",
      interval: "每隔",
      interval_time_unit: {
        millisecond: "毫秒",
        second: "秒",
        minute: "分鐘",
        hour: "小時",
        day: "天",
      },
    },
  },
  // 組織相關國際化
  org_manage: {
    affiliated_organization: "所屬組織",
    organization_name_is_not_empty: "組織名稱不能為空",
    organization_description_is_not_empty: "組織描述不能為空",
  },
  log_manage: {
    login: "登入日誌",
    vm: "雲主機操作日誌",
    disk: "磁片操作日誌",
    platform: "平臺管理日誌",
    operator: "操作人",
    module: "模塊",
    menu: "選單",
    type: "操作類型",
    resource: "操作對象",
    ip: "操作IP",
    status: "操作狀態",
    view_details: "查看詳情",
    belong_vm: "所屬虛擬機器",
    content: "操作詳情",
    login_time: "登入時間",
    op_time: "操作時間",
    btn: {
      clear_policy: "清空策略",
    },
  },
  system_setting: {
    params_setting: {
      recycle_bin: {
        strategy: "回收站策略",
        open: "開啟回收站",
        recycle_strategy: "回收站策略",
        tips: "註意:",
        tips_1:
          "開啟回收站功能後，用戶刪除雲主機、雲磁盤等資源時會將所刪資源放入回收站，不會立即刪除。",
        tips_2:
          "關閉回收站功能後，用戶刪除雲主機、雲磁盤等資源時會立即刪除無法恢復。",
      },
    },
  },
};

export default {
  ...message,
};