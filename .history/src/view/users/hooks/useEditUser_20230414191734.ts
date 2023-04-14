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
    password:'**********',
  })
  //编辑用户表单规则
  const editUserRules = reactive({
    dbname: [
      { required: true, message: '请输入数据库名称', trigger: 'blur' },
      { min: 0, max: 30, message: '请输入正确的数据库名称', trigger: 'blur' },
    ],
    dtype: [
      { required: true, message: '请输入数据源类型', trigger: 'blur' },
      { max: 20, message: '数据源类型必须在20位之内', trigger: 'blur' }
    ],
    ip: [
      { required: true, message: '请输入ip', trigger: 'blur' },
      { min: 1, max: 15, message: '请输入ip', trigger: 'blur' },
    ],
    user: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 0, max: 15, message: '请输入15位以内的用户名', trigger: 'blur' },
    ],
    password:[
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: -1, max: 15, message: '请输入15位以内的密码', trigger: 'blur' },
    ],
  })
  //点击编辑按钮获得用户信息
  const editGetUser = ( id: number, ip: string, user: string,dtype:number,dbname: string,password?:string) => {
    editUserdialogVisible.value = true
    editUserform.ip = ip
    editUserform.id = id
    editUserform.user = user
    editUserform.dbname = dbname
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
