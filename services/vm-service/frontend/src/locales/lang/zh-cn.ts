const message = {
  vm_cloud_server: {
    label: {
      ip_address: "IP地址",
    },
    btn: {
      power_on: "启动",
      shutdown: "关机",
      power_off: "关闭电源",
      reboot: "重启",
    },
    message_box: {
      confirm_power_on: "确认启动",
      confirm_shutdown: "确认关机",
      confirm_power_off: "确认关闭电源",
      confirm_reboot: "确认重启",
      confirm_delete: "确认删除",
      confirm_batch_operate: "确认执行批量{0}操作",
      check_vm_tools_status_confirm_shutdown:
        "当前虚拟机未安装VmTools或VmTools未运行，无法软关机，若继续操作则关闭电源，是否继续？",
    },
  },
  vm_cloud_image: {
    label: {
      management_info: "管理信息",
      image_name: "镜像名称",
      image_id: "镜像ID",
    },
    btn: {
      set_management_info: "设置管理信息",
    },
  },
  vm_cloud_disk: {
    label: {
      vm: "所属云主机",
      size: "大小",
      disk_category: "磁盘种类",
      disk_type: "磁盘类型",
      mount_info: "挂载信息",
      delete_with_instance: "随实例删除",
      select_vm: "请选择要挂载的云主机",
      cloudVm: "云主机",
      deleteWithVm: "随云主机删除",
      disk_info: "磁盘信息",
      disk_name: "磁盘名称",
      change_config: "配置变更",
      current_config: "当前配置",
      after_config: "变更后配置",
      disk_size: "磁盘大小",
      system_disk: "系统盘",
      data_disk: "数据盘",
      disk_id: "磁盘ID",
    },
    btn: {
      create: "添加磁盘",
      enlarge: "扩容",
      uninstall: "卸载",
      mount: "挂载",
      delete: "删除",
    },
    confirm: {
      detach: "确认将云磁盘{0}从云主机{1}上卸载",
      delete: "确认将云磁盘{0}删除",
      batch_detach: "确认批量卸载云磁盘",
      batch_delete: "确认批量删除云磁盘",
    },
    msg: {
      canceled: "已取消{0}",
      select_one: "至少选择一条记录",
      datastore_null: "存储器不能为空",
    },
  },
};
export default {
  ...message,
};
