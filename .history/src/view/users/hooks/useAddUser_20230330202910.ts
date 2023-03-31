import { userStore } from '@/store/user'
import { reactive, ref } from 'vue'
export  function useAddUser (pageSize: number, pageNum: number) {
  const store = userStore()
  const addUserdialogVisible = ref(false) //控制添加用户对话框的显示
  const addUserFormRef = ref()  // 添加用户表单的ref
   // 添加用户的表单
  const addUserform = reactive({
    username: '',
    password: '',
    datasource_type:'',
    email: '',
    mobile: ''
  })
 // 添加用户的规则
  const addUserRules = reactive({
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 3, max: 10, message: '用户名必须在3到10位之间', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 10, message: '密码必须在5到10位之间', trigger: 'blur' }
    ],
    datasource_type: [
      { required: true, message: '请输入数据源名称', trigger: 'blur' },
      { min: 1, max: 20, message: '数据源名称必须在1到20位之间', trigger: 'blur' }
    ],
    email: [
      { required: true, message: '请输入数据库名称', trigger: 'blur' },
      { min: 1, max: 30, message: '请输入正确的数据库名称', trigger: 'blur' },
      
    ],
    mobile: [
      { required: true, message: '请输入ip', trigger: 'blur' },
      { min: 1, max: 15, message: '请输入ip', trigger: 'blur' },
     
    ]
  })
  //点击确认添加用户
  const addUser = () => {
    console.log(addUserform);
    addUserFormRef.value.validate((isValidate: boolean) => {
      if (isValidate) {
        store.addUser(addUserform).then(() => {
          store.getUsers({ pagenum: pageNum, pagesize: pageSize })
          addUserFormRef.value.resetFields()
          addUserdialogVisible.value = false
        })
      }
    })
  }
  //关闭添加用户对话框的回调
const addDialogClose = () => {
  addUserFormRef.value.resetFields()
}
  return{
    addUserdialogVisible,
    addUserFormRef,
    addUserform,
    addUserRules,
    addDialogClose,
    addUser

  }
}
