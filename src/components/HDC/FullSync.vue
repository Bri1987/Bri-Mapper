<template>
    <a-layout style="background: rgba(255, 255, 255, 0);">
        <div class="greycover" v-show="grey"></div>
        <a-layout-header style="background: rgba(255, 255, 255, 0);">
            <a-typography-title :level="3" style="text-align: left;color:#1da57a">
                <cloud-sync-outlined />
                全量同步
            </a-typography-title>
            <a-divider style="border-color: #7cb305" dashed />
        </a-layout-header>
        <a-layout-content style="position:fixed;left:420px;top:200px;font-size: larger;font-weight: 550;">
            <br>
            <br>
            <a-form :model="newSubmit">
                <a-form-item label="table_name" style="width: 600px;margin-bottom: 60px;">
                    <a-input v-model:value="newSubmit.table_name"/>
                </a-form-item>
                <a-row :gutter="[48,48]">
                    <a-col :span="96" style="width: 400px;">
                        <a-form-item label="第一个文件id：" style="margin-bottom: 60px;">
                        <a-input v-model:value="newSubmit.id1"/>
                    </a-form-item>
                    <a-form-item label="上传第一个文件">
                        <a-upload 
                            :file-list="filelist1"
                            name="first"
                            list-type="picture-card"
                            class="first"   
                            @change="handleChange1"
                            :show-upload-list="false"
                            :customRequest="file=>uploadForm1(file)"
                            maxCount="1"
                        >
                            <div>
                                <LoadingOutlined v-if="loading"></LoadingOutlined>
                                <plus-outlined v-else>+</plus-outlined>
                                <div class="ant-upload-text">Upload</div>
                            </div>
                        </a-upload>
                        <vertical-align-top-outlined v-if="done1"/>{{ newSubmit.file1.name}}
                    </a-form-item>
                    </a-col>
                    <a-col :span="96" style="width: 400px;">
                        <a-form-item label="第二个文件id：" style="margin-bottom: 60px;">
                        <a-input v-model:value="newSubmit.id2"/>
                    </a-form-item>
                    <a-form-item label="上传第二个文件">
                        <a-upload 
                            v-model:file-list="filelist2"
                            name="second"
                            list-type="picture-card"
                            class="second"
                            @change="handleChange2"
                            :show-upload-list="false"
                            :customRequest="uploadForm2"
                            >
                            <!-- <img v-if="imgeUrl" :src="imgeUrl" alt="second"/> -->
                            <div>
                                <loading-outlined v-if="loading"></loading-outlined>
                                <PlusOutlined v-else></PlusOutlined>
                                <div class="ant-upload-text">Upload</div>
                            </div>
                        </a-upload>
                        <vertical-align-top-outlined v-if="done2"/>{{ newSubmit.file2.name}}
                    </a-form-item>
                    </a-col>
                </a-row>
                <a-form-item :wrapper-col="{ offset: 8, span: 16 }" style="position:fixed;bottom: 15%;right:20%">
                    <a-button type="primary" @click="onSubmit">Submit</a-button>
                </a-form-item>
            </a-form>
            
        </a-layout-content>
            <a-card title="详细信息" :bordered="false" class="details" v-show="visible">
            <close-circle-outlined class="exit" @click="exit_details"/>
            <a-list>
                <a-list-item v-for="(value,key,index) in detailInfo" :key="index">
                    {{ key }} : {{ value }}
                </a-list-item>
            </a-list>
        </a-card>
    </a-layout>
</template>
  
<script>
import { LoadingOutlined,PlusOutlined,VerticalAlignTopOutlined,CloudSyncOutlined,CloseCircleOutlined} from '@ant-design/icons-vue';
import { defineComponent, reactive, toRaw } from 'vue';
import axios from 'axios'
export default{
    components:{
        PlusOutlined,LoadingOutlined,VerticalAlignTopOutlined,CloudSyncOutlined,CloseCircleOutlined
    },
    data(){
        return {
            newSubmit:{
                table_name: '',
                id1:null,
                id2: null,
                file1: {},
                file2: {},
            },
            loading:false,
            done1:false,
            done2:false,
            visible:false,
            detailInfo:{},
            grey:false
        }
    },
    methods:{
        onSubmit(){
            // this.showModal()//测试
            // console.log('submit!', toRaw(this.newSubmit));//这里写表单的上传方法    
            axios.post('http://localhost:8121/function/exchange',this.newSubmit).then(res=>{
                console.log(res)
                if(res.status==200){
                    this.detailInfo=res.data
                    this.showModal()
                    //显示一个小卡片
                    //!!!治理是不是该返回一个什么数据
                    // this.showModal()
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        showModal(){
            this.visible=true
            this.grey=true
        },
        handleOk(){
            this.visible=false
        },
        uploadForm1(filelist1){
            this.newSubmit.file1=filelist1.file
            // console.log(filelist1.file)
        },
        uploadForm2(filelist2){
            this.newSubmit.file2=filelist2.file
            // console.log(filelist2.file)
        },
        handleChange1(info){
            console.log("handleChange1",info.file.name)
            if(info.file.status==='uploading') {
                this.done1=true
            }
        },
        handleChange2(info){
            console.log("handleChange2",info.file.name)
            if(info.file.status==='uploading') {
                this.done2=true
            }
        },
        exit_details(){
            this.visible=false
            this.grey=false
            this.detailInfo={}
        }
    },
};
</script>
<style scoped>
.greycover{
    position: fixed;
    height: 100%;
    width: 100%;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background: rgba(187, 187, 187, 0.5);
    z-index: 9;
}
.details{
    z-index: 999;
    width: 600px;
    position: fixed;
    top:19%;
    left:36%
}
.exit{
    position: absolute;
    height: 2em;
    width: 2em;
    right: 25px;
    top:25px
}
.exit:hover{
   color: green;
   font-size: larger;
}
</style>