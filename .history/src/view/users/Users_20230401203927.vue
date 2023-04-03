<template>
  <a-layout class="users">
    <canvass></canvass>
    <a-layout-content style="padding: 0 50px; position:fixed;left:5%;right: 5%;bottom: 5%;top:10%;background: rgba(0, 0, 0, 0);">
      <a-breadcrumb style="margin: 16px 0">
        <a-breadcrumb-item>数据源</a-breadcrumb-item>
      </a-breadcrumb>
    <!-- 查询 -->
      <el-card class="box-card">
        <el-form :model="userForm">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item>
                <el-input placeholder="请输入数据源" width="200px" v-model.number="query" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-button type="primary" :icon="Search" @click="searchUser">查询</el-button>
              <el-button :icon="Refresh" @click="resetForm">重置</el-button>
            </el-col>
          </el-row>
        </el-form>
        <!-- 数据源列表 -->
        <div class="user-list">
          数据源列表
          <el-button type="primary" @click="addUserdialogVisible = true" class="add-btn">
            添加数据源
          </el-button>
        </div>
        <el-table :data="users" stripe style="width: 100%" border>
          <el-table-column type="index" width="60" label="序号" />
          <el-table-column prop="ip" label="ip" />
          <el-table-column prop="user" label="用户名" />
          <el-table-column prop="dtype" label="数据源类型" />
          <el-table-column prop="dbname" label="数据库名称" />
          
          
          <el-table-column label="操作" width="300">
            <template #default="scope">
              <div class="control">
                <el-link
                  type="primary"
                  :icon="EditPen"
                  :underline="false"
                  @click="
                    editGetUser(scope.row.user, scope.row.dbname, scope.row.ip, scope.row.id,scope.row.dtype)
                  "
                  >编辑</el-link
                >
                <el-link
                  type="danger"
                  :icon="Delete"
                  :underline="false"
                  @click="deleteUser(scope.row.id, scope.row.user)"
                >
                  删除
                </el-link>
                
              </div>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
          v-model:currentPage="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[10, 15, 20]"
          layout="total, sizes, prev, pager, next"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          class="pagination"
        />
      </el-card>
      <!-- 添加数据源对话框 -->
      <el-dialog v-model="addUserdialogVisible" title="添加数据源" width="30%" @close="addDialogClose">
        <el-form :model="addUserform" label-width="80px" :rules="addUserRules" ref="addUserFormRef">
          <el-form-item label="ip" prop="ip">
            <el-input v-model="addUserform.ip" />
          </el-form-item>
          <el-form-item label="用户名" prop="user">
            <el-input v-model="addUserform.user" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="addUserform.password" />
          </el-form-item>
          <el-form-item label="数据源类型" prop="dtype">
            <el-input v-model="addUserform.dtype" />
          </el-form-item>
          <el-form-item label="数据库名称" prop="dbname">
            <el-input v-model="addUserform.dbname" />
          </el-form-item>

        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="addUserdialogVisible = false">取消</el-button>
            <el-button type="primary" @click="addUser">确认</el-button>
          </span>
        </template>
      </el-dialog>
      <!-- 编辑数据源对话框 -->
      <el-dialog v-model="editUserdialogVisible" title="编辑数据源" width="30%">
        <el-form
          :model="editUserform"
          label-width="80px"
          :rules="editUserRules"
          ref="editUserFormRef"
        >
        <el-form-item label="ip" prop="ip">
          <el-input v-model="editUserform.ip" />
        </el-form-item>
          <el-form-item label="用户名" prop="user">
            <el-input v-model="editUserform.user" disabled />
          </el-form-item>
          <el-form-item label="数据源类型" prop="dtype">
            <el-input v-model="editUserform.dtype" />
          </el-form-item>
          <el-form-item label="数据库名称" prop="dbname">
            <el-input v-model="editUserform.dbname" />
          </el-form-item>

        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="editUserdialogVisible = false">取消</el-button>
            <el-button type="primary" @click="editUser">确认</el-button>
          </span>
        </template>
      </el-dialog>
  </a-layout-content>
  </a-layout>
</template>

<script setup lang="ts">
import canvass from '@/components/bcg1.vue'
import { Search, Refresh, EditPen, Delete, Setting } from '@element-plus/icons-vue'
import { reactive, ref, onMounted } from 'vue'
import { userStore } from '@/store/user'
import { storeToRefs } from 'pinia'
import { ElMessageBox } from 'element-plus'
import { useAddUser } from './hooks/useAddUser'
import { useEditUser } from './hooks/useEditUser'

const store = userStore()
const { users, total, roles } = storeToRefs(store)
let pageSize = ref(10) //每页显示条数
let pageNum = ref(1) //当前页码
const { addUserdialogVisible, addUserFormRef, addUserform, addUserRules, addUser, addDialogClose } =
  useAddUser(pageSize.value, pageNum.value)
const {
  editUserdialogVisible,
  editUserFormRef,
  editUserform,
  editUserRules,
  editGetUser,
  editUser
} = useEditUser(pageSize.value, pageNum.value) //编辑数据源

const userForm = reactive({
  id: ''
})
let query = ref('') //查询关键词
//每页显示条数改变触发
const handleSizeChange = (newPageSize: number) => {
  store.getUsers({ pagenum: pageNum.value, pagesize: newPageSize })
  pageSize.value = newPageSize
}
//页码改变触发
const handleCurrentChange = (newPageNum: number) => {
  store.getUsers({ pagenum: newPageNum, pagesize: pageSize.value })
  pageNum.value = newPageNum
}
//根据id查询
const searchUser = () => {
  if (query.value === '') {
    store.getUsers({ pagenum: 1, pagesize: 10 })
    pageNum.value = 1
    pageSize.value = 10
  } else {
    store.getUsersFromId( query: query.value )
  }
}
//删除数据源
const deleteUser = (id: number, user: string) => {
  ElMessageBox.confirm(`确认删除数据源${user}吗`, '删除数据源', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(() => {
    store.deleteUsersFromId(id).then(() => {
      store.getUsers({ pagenum: pageNum.value, pagesize: pageSize.value })
    })
  })
}

//修改数据源状态
// const changeStatus = (uid: number, type: boolean) => {
//   store.changeUserStatus({ uid, type })
// }
//重置
const resetForm = () => {
  query.value = ''
  store.getUsers({ pagenum: 1, pagesize: 10 })
  pageNum.value = 1
  pageSize.value = 10
}
onMounted(() => {
  store.getUsers({ pagenum: 1, pagesize: 10 })
})
</script>
<style scoped lang="less">
.users{
  position:fixed;
    right: 0;
    left:0;
    bottom: 0%;
    height:94%;
    z-index: 999;
}
.box-card{
  background: rgba(255,255,255,0.6);
}
.el-link {
  margin-right: 15px;
}
.pagination {
  margin-top: 20px;
}

.user-list {
  position: relative;
  height: 40px;
  .add-btn {
    position: absolute;
    right: 10px;
    bottom: 15px;
  }
}
</style>
