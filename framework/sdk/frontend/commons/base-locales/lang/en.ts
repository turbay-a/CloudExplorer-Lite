import elementEnLocale from "element-plus/lib/locale/lang/en";
import fit2cloudEnLocale from "fit2cloud-ui-plus/src/locale/lang/en";
import subModuleEnLocale from "@/locales/lang/en";

const message = {
  commons: {
    home: "Home Page",
    notice: "Notice",
    to_do_list: "To Do List",
    view_all: "View All",
    operation: "Operation",
    name: "Name",
    tag: "Tag",
    org: "Organization",
    workspace: "Workspace",
    os: "Operating System",
    os_version: "Operating System Version",
    status: "Status",
    create_time: "Create Time",
    update_time: "Update Time",
    description: "Description",
    basic_info: "Basic Information",
    grant: "Grant",
    cancel_grant: "Cancel Grant",
    org_workspace: "Organization Or Workspace",
    cloud_account: {
      native: "Cloud Account",
      name: "Cloud Account Name",
      data_center: "Data Center",
      cluster: "Cluster",
      region: "Region",
      zone: "Zone",
      host: "Host",
      storage: "Storage",
      disk: "Disk",
      vm: "Virtual Machine",
      image: "Image",
    },
    cloud_server: {
      instance_type: "Instance Type",
      applicant: "Applicant",
      more: "More",
    },
    message_box: {
      alert: "Alert",
      confirm: "Confirm",
      prompt: "Prompt",
      confirm_delete: "Confirm Delete",
    },
    btn: {
      login: "Login",
      yes: "Yes",
      no: "No",
      ok: "OK",
      add: "Add",
      create: "Create",
      delete: "Delete",
      edit: "Edit",
      save: "Save",
      close: "Close",
      submit: "Submit",
      publish: "Publish",
      cancel: "Cancel",
      return: "Return",
      grant: "Grant",
      hide: "Hide",
      display: "Display",
      enable: "Enable",
      disable: "Disable",
      copy: "copy",
      sync: "Synchronize",
      view_api: "View API",
      prev: "Previous",
      next: "Next",
      switch_lang: "Switch Language",
      add_favorites: "Add To Favorites",
      cancel_favorites: "Cancel Favorites",
      search: "Search",
      refresh: "Refresh",
      import: "Import",
      export: "Export",
      upload: "Upload",
      download: "Download",
      more_actions: "More Actions",
    },
    msg: {
      success: "{0}Success",
      op_success: "Success",
      save_success: "Save Success",
      delete_success: "Delete Success",
      fail: "{0} Failed",
      delete_canceled: "Delete Canceled",
      at_least_select_one: "At Least Select One Data",
    },
    validate: {
      required: "{0} Required",
      limit: "Length Is Between {0} 到 {1}",
      input: "Please Input{0}",
      select: "Please Select{0}",
      confirm_pwd: "The Two Passwords You Entered Were Inconsistent",
      pwd: "Valid password: 8-30 digits, English upper and lower case letters + numbers + special characters",
    },
    personal: {
      personal_info: "Personal Detail",
      edit_pwd: "Edit Password",
      help_document: "Help Document",
      exit_system: "Exit System",
      old_password: "Old Password",
      new_password: "New Password",
      confirm_password: "Confirm Password",
      login_identifier: "Login Identifier",
      username: "Username",
      phone: "Phone",
      wechat: "Wechat",
    },
    date: {
      select_date: "Select Date",
      start_date: "Start Date",
      end_date: "End Date",
      select_time: "Select Time",
      start_time: "Start Time",
      end_time: "End Time",
      select_date_time: "Select Date Time",
      start_date_time: "Start Date Time",
      end_date_time: "End Date Time",
      range_separator: "To",
      date_time_error: "Start Time Can Not Be Greater Than End Time",
    },
    login: {
      username: "Username",
      password: "Password",
      title: "CloudExplorer Cloud Service Platform",
      welcome:
        "Welcome Back, Please Enter Your User Name and Password to Login",
      expires: "The Authentication Information Has Expired, Please Login Again",
    },
    charge_type: {
      native: "Charge Type",
      prepaid: "Prepaid",
      postpaid: "Postpaid",
    },
  },
};

const permissions = {
  permission: {
    manage: {
      user: {
        base: "User",
        read: "Read",
        create: "Create",
        edit: "Edit",
        delete: "Delete",
      },
      role: {
        base: "Role",
        read: "Read",
        create: "Create",
        edit: "Edit",
        delete: "Delete",
      },
      organization: {
        base: "Organization",
        read: "Read",
        create: "Create",
        edit: "Edit",
        delete: "Delete",
      },
      workspace: {
        base: "Workspace",
        read: "Read",
        create: "Create",
        edit: "Edit",
        delete: "Delete",
      },
      cloud_account: {
        base: "Cloud Account",
        read: "Read",
        create: "Create",
        edit: "Edit",
        delete: "Delete",
        sync_resource: "Sync Resources",
        sync_bill: "Sync Bill",
        sync_setting: "Sync Settings",
      },
      sys_log: {
        base: "System Logs",
        read: "Read",
      },
      operated_log: {
        base: "Operation Logs",
        read: "Read",
      },
    },
    vm: {
      cloud_server: {
        base: "Cloud Server",
        read: "Read",
        create: "Create",
        edit: "Edit",
        delete: "Delete",
        start: "Start",
        stop: "Stop",
        restart: "Restart",
        resize: "Resize",
        auth: "Auth",
      },
      cloud_disk: {
        base: "Cloud Disk",
        read: "Read",
        create: "Create",
        edit: "Edit",
        delete: "Delete",
        attach: "Attach",
        detach: "Detach",
        resize: "Resize",
        auth: "Auth",
      },
      cloud_image: {
        base: "Cloud Image",
        read: "Read",
      },
      jobs: {
        base: "Job Records",
        read: "Read",
      },
    },
  },
};

export default {
  ...elementEnLocale,
  ...fit2cloudEnLocale,
  ...message,
  ...permissions,
  ...subModuleEnLocale,
};
