<template>
  <div>
    <a-layout>
      <a-page-header
        style="text-align: center; border: 1px solid rgb(235, 237, 240)"
        title=">对象可视化"
        sub-title="请先上传元数据对象"
      />
    </a-layout>
    <a-upload
      :multiple="false"
      :file-list="fileList"
      @change="handleChange1"
      :customRequest="file=>uploadFile1(file)"
      accept=".csv"
    >
      <a-button style="position:absolute;right:10%;top:16%;color:white;background:#1da57a">
        <upload-outlined></upload-outlined>
        Upload
      </a-button>
    </a-upload>
    <a-layout-content class="bingtu" id="main">
    </a-layout-content>
    <!-- <a-empty class="emptyStatus" v-if="empty">
        <template #description>暂无数据，请先上传文件</template>
    </a-empty> -->
    
  </div>
</template>
<script lang="js">
import { UploadOutlined ,CloudUploadOutlined} from '@ant-design/icons-vue';
import { defineComponent, ref } from 'vue';
import * as echarts from "echarts";

export default{
    components:{
        UploadOutlined ,CloudUploadOutlined 
    },
    data(){
        return {
            thisfile:{},
            fileList:[],
            empty:true
        }
    },
    methods:{
        uploadFile1(file){    
            // this.showBing(0)
            this.thisfile=file.file
            var form=new FormData()
            form.append('file',this.thisfile)
            this.$axios.post("http://localhost:8121/function/visualize",form)
            .then(res=>{
                console.log(res)
                if(res.status==200)
                {
                    console.log(res.data.data)
                    //这里展示饼
                    this.showBing(res.data.data)
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        handleChange1(info){
            console.log("handleChange1",info.file.name)
            if (info.file.status !== 'uploading') {
                console.log(info.file, info.fileList);
            }
        },
        showBing(datas){
            // datas={
            //     "人员类": 57,
            //     "信息资源": 21,
            //     "元素及其修饰词": 48,
            //     "军民通用资源数据元编制": 26,
            //     "分类属性": 8,
            //     "固定资产": 41,
            //     "扩展元数据": 6,
            //     "报文结构及标记": 27,
            //     "核心元数据": 14,
            //     "注册": 65,
            //     "科技人才专长":4, 
            //     "科技人才任职":6,
            //     "科技人才培训交流经历":7,
            //     "科技人才基本信息":46,
            //     "科技人才奖励信息":9,
            //     "科技人才工作履历":11,
            //     "科技人才惩罚信息":82,
            //     "科技人才教育经历":10,
            //     "科技人才荣誉信息":6,
            //     "科技平台":19,
            //     "科技资源":18,
            //     "设施类":79
            //       }
            var values=[]
            var names=[]
            for(var val in datas){
                console.log(val,datas[val])
                values.push(datas[val])
                names.push(val)
            }
            console.log(values.length)
            console.log(names)
            var data=[]
            for(let i=0;i<values.length;i++){
                var obj={
                    value:values[i],
                    name:names[i]
                }
                data.push(obj)
            }
            console.log(data)
            const chartBox = echarts.init(document.getElementById("main"));
            const option = {
                tooltip: {
                    trigger: 'item'
                },
                series: [
                    {
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        data:data
                    }
                ]
            };
            option && chartBox.setOption(option);
        }
    }
}
</script>

<style scoped>
.bingtu{
    position: absolute;
    height: 60%;
    width: 70%;
    background-color: rgba(255,255,255,0.6);
}
.emptyStatus{
    position: absolute;
    height: 60%;
    width: 70%;
    top:40%
}
</style>
