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
        <a-layout-content style="position:fixed;left:480px;top:200px;font-size: larger;font-weight: 550;">
            <br>
            <br>
            <a-form :model="newSubmit">
                <a-form-item label="table_name" style="width: 540px;margin-bottom: 60px;">
                    <a-input v-model:value="newSubmit.table_name"/>
                </a-form-item>
                <a-row :gutter="[48,48]">
                    <a-col :span="96" style="width: 360px;">
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
                            accept=".xml"
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
                    <a-col :span="96" style="width: 360px;">
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
                            accept=".xml"
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
                <a-layout-content class="bc">
                    <div class="scrolls">
                        <a-row :gutter="[24,48]" style="margin-bottom: 32px;" >
                            <a-col :span="8" v-for="(data,index) in detailInfo" :key="index">
                                <a-card class="wholecards" :bordered="false" hoverable="true">
                                    <template #title>
                                        <heat-map-outlined />
                                        {{ data.科技平台服务资源名称}}
                                    </template>
                                    <a-list>
                                        <a-list-item v-for="(value,key,indix) in data" :key="index" style="font-size:small">
                                            {{ key }}:{{ value }}
                                        </a-list-item>
                                    </a-list>
                                </a-card>
                            </a-col>
                        </a-row>
                    </div>
                </a-layout-content>
            </a-card>
    </a-layout>
</template>
  
<script>
import { LoadingOutlined,PlusOutlined,VerticalAlignTopOutlined,CloudSyncOutlined,CloseCircleOutlined,HeatMapOutlined} from '@ant-design/icons-vue';
import { defineComponent, reactive, toRaw } from 'vue';
import axios from 'axios'
export default{
    components:{
        PlusOutlined,LoadingOutlined,VerticalAlignTopOutlined,CloudSyncOutlined,CloseCircleOutlined,HeatMapOutlined
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
            visible:false,//test false
            detailInfo:{},
            grey:false, //test false
        }
    },
    methods:{
        onSubmit(){
            // this.showModal()//测试
            var form = new FormData()
            form.append('id1',this.newSubmit.id1)
            form.append('id2',this.newSubmit.id2)
            form.append('file1',this.newSubmit.file1)
            form.append('file2',this.newSubmit.file2)
            form.append('table_name',this.newSubmit.table_name)

            this.$axios.post('http://localhost:8121/function/exchange',form)
            .then(res=>{
                console.log(res)
                if(res.status==200){
                    this.detailInfo=res.data.data
                    this.showModal()
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        showModal(){
            this.visible=true
            // this.grey=true
        },
        handleOk(){
            this.visible=false
        },
        uploadForm1(filelist1){
            this.newSubmit.file1=filelist1.file
        },
        uploadForm2(filelist2){
            this.newSubmit.file2=filelist2.file
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
    background: rgba(187, 187, 187, 0.7);
    z-index: 9;
}
.details{
    border-radius: 1%;
    z-index: 999;
    width: 100%;
    height: 100%;
    position: absolute;
    top:0%;
    left:0%;
    padding: 0%;
     background-color: rgba(187,187,187,0);
     background-color:rgba(111, 224, 196, 0.981);
    /*background: rgba(255, 255, 255,0.6) linear-gradient(70deg, #488cba, #909ff3, #b375dc);*/
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
.bc{
    height: 85%;
    left: 2%;
    width: 96%;
    position: absolute;
    overflow: hidden;
}
.scrolls{
    position: absolute;height:100%;left:0;right: -17px;top:0;bottom: 0;overflow-x: hidden;overflow-y: scroll;
}
.wholecards{
    border-radius: 5%;
    box-shadow: 0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23);
}
.wholecards:hover{
    box-shadow: 0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23);
}
</style>