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
    <button @click="showBing">点击</button>
    <a-layout-content class="bingtu" id="main">
        jodjwi
    </a-layout-content>
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
            fileList:[]
        }
    },
    methods:{
        uploadFile1(file){        
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
                    // this.showBing(res.data.data)
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
            for ();
            const chartBox = echarts.init(document.getElementById("main"));
            const option = {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
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
                        data: [
                            { value: 1048, name: 'Search Engine' },
                            { value: 735, name: 'Direct' },
                            { value: 580, name: 'Email' },
                            { value: 484, name: 'Union Ads' },
                            { value: 300, name: 'Video Ads' }
                        ]
                    }
                ]
            };
            option && chartBox.setOption(option);
        }
        

    }
}
// export default defineComponent({
//   components: {
//     UploadOutlined,CloudUploadOutlined
//   },
//   setup() {
//     const handleChange = info => {
//         if (info.file.status !== 'uploading') {
//         console.log(info.file, info.fileList);
//       }
//       if (info.file.status === 'done') {
//         message.success(`${info.file.name} file uploaded successfully`);
//       } else if (info.file.status === 'error') {
//         message.error(`${info.file.name} file upload failed.`);
//       }
//     };
//     const fileList = ref([]);
//     const thisfile={}
//     const uploadFile=file=>{
//         thisfile=fileList.file
//         var form=new FormData()
//         form.append('file',thisfile)
//         axios.post("http://localhost:8121/function/visualize",form)
//         .then(res=>{
//             console.log(res)
//             if(res.status==200)
//             {
//                 console.log(res.data.data)
//                 //这里展示饼
//                 // showBing(res.data.data)
//             }
//         }).catch(err=>{
//             console.log(err)
//         })
//     }

//     const showBing=data=>{
//         //展示饼图
//         // const bingtu=echarts.init(document.getElemetById("main"))
//     }
//     return {
//       fileList,
//       handleChange,
//       uploadFile
//     };
//   },
// });
</script>
<style>
.bingtu{
    position: absolute;
    height: 60%;
    width: 70%;
    background-color: #fff;
}
</style>
