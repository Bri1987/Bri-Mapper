<template>
    <div>
        <a-table :columns="columns" :data-source="dataSource" bordered>
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
        
    </div>
</template>

<script>
import { cloneDeep } from 'lodash-es';
import { defineComponent, reactive, ref } from 'vue';
//-----------------------------------------------------------------------------------------------------------
//table初始化配置
const columns = [{
        title: 'id',
        dataIndex: 'id',
        width: '5%',
        }, {
        title: 'ip地址',
        dataIndex: 'ip',
        width: '15%',
        }, {
        title: '用户名',
        dataIndex: 'username',
        width: '40%',
        }, {
        title: 'operation',
        dataIndex: 'operation',
    }];
// const data = [{id:'1',ip:'0',username:"lily"},{id:'1',ip:'0',username:"lily"}];
export default ({
  setup() {
    const data=[]
    const ws=new WebSocket('ws://localhost:8119/datasource/list')
    ws.onopen=function(){
        console.log('连接成功')
    }
    ws.onerror=function(){
        console.log('连接失败')
    }
    ws.onmessage=function(e){
        data=JSON.parse(e.data)
    }
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

<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>