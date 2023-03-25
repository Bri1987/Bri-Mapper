<template>
    <a-table :columns="columns" :data-source="data" :scroll="{ x: 1500, y: 300 }" bordered>
            <template #bodyCell="{ column, text, record }">
                <template v-if="[ 'ip', 'username'].includes(column.dataIndex)">
                    <div>
                    <a-input
                        v-if="editableData[record.key]"
                        v-model:value="editableData[record.key][column.dataIndex]"
                        style="margin: -5px 0"
                    />
                    <template v-else>
                        {{ text }}
                    </template>
                    </div>
                </template>
                <template v-else-if="column.dataIndex === 'operation'">
                    <div class="editable-row-operations">
                    <span v-if="editableData[record.key]">
                        <a-typography-link @click="save(record.key)">Save</a-typography-link>
                        <a-popconfirm title="Sure to cancel?" @confirm="cancel(record.key)">
                        <a>Cancel</a>
                        </a-popconfirm>
                    </span>
                    <span v-else>
                        <a @click="edit(record.key)">Edit</a>
                    </span>
                    </div>
                </template>
            </template>
        </a-table>
</template>

<script>
import { cloneDeep } from 'lodash-es';
import { defineComponent, reactive, ref } from 'vue';
const columns = [{
        title: 'id',
        dataIndex: 'id',
        width: 50,
        fixed:'left'
        }, {
        title: 'ip地址',
        dataIndex: 'ip',
        width: 80,
        }, {
        title: '用户名',
        dataIndex: 'username',
        width: 80,
        }, {
        title: '用户密码',
        dataIndex: 'password',
        width: '40%',
        }, {
        title: '数据库名称',
        dataIndex: 'dbname',
        width: '40%',
        },  {
        title: '数据类型',
        dataIndex: 'datasourceType',
        width: '40%',
        },{
        title: 'operation',
        dataIndex: 'operation',
    }];
// const data = [{id:'1',ip:'0',username:"lily"},{id:'1',ip:'0',username:"lily"}];
export default defineComponent({
  setup() {
    const data=[]
    
//=============table=================================================================
    const dataSource = ref(data);
    const editableData = reactive({});
    const edit = key => {
      editableData[key] = cloneDeep(dataSource.value.filter(item => key === item.key)[0]);
    };
    const save = key => {
      Object.assign(dataSource.value.filter(item => key === item.key)[0], editableData[key]);
      delete editableData[key];
    };
    const cancel = key => {
      delete editableData[key];
    };
    return {
      dataSource,
      columns,
      editingKey: '',
      editableData,
      edit,
      save,
      cancel,
    };
  },
});

//------------------------------------------------------------------------------------------------------------------------------------


</script>
<style>
.editable-row-operations a {
  margin-right: 8px;
}
</style>