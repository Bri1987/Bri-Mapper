import { userStore } from '@/store/user'
import { reactive, ref } from 'vue'
export function useEditUser(pageSize: number, pageNum: number) {
  const store = userStore()
  const editUserdialogVisible = ref(false)//控制编辑用户对话框的显示
  const editUserFormRef = ref()//编辑用户表单的ref
  //编辑用户表单
  const editUserform = reactive({
    id: -1,
    user: '',
    dtype:0,
    dbname: '',
    ip: '',
    password:'',
  })
  //编辑用户表单规则
  const editUserRules = reactive({
    dbname: [
      { required: true, message: '请输入数据库名称', trigger: 'blur' },
      { min: 1, max: 30, message: '请输入正确的数据库名称', trigger: 'blur' },
    ],
    dtype: [
      { required: true, message: '请输入数据源名称', trigger: 'blur' },
      { min: 0, max: 20, message: '数据源名称必须在1到20位之间', trigger: 'blur' }
    ],
    ip: [
      { required: true, message: '请输入ip', trigger: 'blur' },
      { min: 1, max: 15, message: '请输入ip', trigger: 'blur' },
    ],
    
  })
  //点击编辑按钮获得用户信息
  const editGetUser = (password?:string,user: string, dbname: string, ip: string, id: number,dtype:number) => {
    editUserdialogVisible.value = true
    editUserform.user = user
    editUserform.dbname = dbname
    editUserform.ip = ip
    editUserform.id = id
    editUserform.dtype= dtype
  }
  //点击确认编修改用户信息
  const editUser = () => {
    editUserFormRef.value.validate((isValidate: boolean) => {
      if (isValidate) {
        store.editUsersFromId(editUserform).then(() => {
          store.getUsers({ pagenum: pageNum, pagesize: pageSize })
        })
      }
    })
    editUserdialogVisible.value = false
  }
  return {
    editUserdialogVisible,
    editUserFormRef,
    editUserform,
    editUserRules,
    editGetUser,
    editUser
  }
}
