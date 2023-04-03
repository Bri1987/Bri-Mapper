<template>
    <a-layout class="top">
        <canvass></canvass>
        <a-layout-content style="padding: 0 50px; position:fixed;left:5%;right: 5%;bottom: 5%;top:10%;">
            <a-breadcrumb style="margin: 16px 0">
                <a-breadcrumb-item>HDB</a-breadcrumb-item>
            </a-breadcrumb>
            <a-layout-content class="precfg" v-if="shift">
                <a-page-header
                    style="text-align: center;border: 1px solid rgb(235, 237, 240);top:0%;position: absolute;width: 100%;"
                    title=">异构数据库"
                    sub-title="请先填写并提交表单"
                />
                <br><br><br><br>
                <a-form :model="newSubmit" style="position: absolute;width: 80%;left: 15%;font-weight:bold;">
                    <a-form-item label="table_name" style="width: 600px;row-gap: 400px;margin-bottom: 60px;">
                        <a-input v-model:value="newSubmit.table_name"/>
                    </a-form-item>
                    <a-row :gutter="48">
                        <a-col :span="100">
                            <a-form-item label="第一个文件id：" >
                            <a-input v-model:value="newSubmit.id1"/>
                        </a-form-item>
                        <a-form-item label="上传第一个文件" style="margin-top: 60px;">
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
                        <a-col>
                            <a-form-item label="第二个文件id：">
                            <a-input v-model:value="newSubmit.id2"/>
                        </a-form-item>
                        <a-form-item label="上传第二个文件" style="margin-top: 60px;">
                            <a-upload 
                                v-model:file-list="filelist2"
                                name="second"
                                list-type="picture-card"
                                class="second"
                                @change="handleChange2"
                                :show-upload-list="false"
                                :customRequest="uploadForm2"
                                >
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
            <a-layout-content class="aftercfg" v-else  style="">
                <a-page-header
                    style="text-align: center;border: 1px solid rgb(235, 237, 240);top:0%;position: absolute;width: 100%;"
                    title=">异构数据库"
                    sub-title="查询结果"
                />
                <!--展示一堆小卡片-->
                <a-layout-content class="results">
                    <div style="position: absolute;height:100%;left:0;right: -17px;top:0;bottom: 0;overflow-x: hidden;overflow-y: scroll;">
                        <a-row :gutter="[32,48]" style="margin-bottom: 32px;" >
                            <a-col :span="8" v-for="(data,index) in resp" :key="index">
                                <a-card class="wholecards" :bordered="false" hoverable="true">
                                    <template #title>
                                        <database-outlined/>&nbsp;&nbsp;
                                        {{ data.科技平台服务资源名称 }}
                                    </template>
                                    <p>内容描述: {{ data.科技平台服务内容 }}</p>
                                    <p>资源标识代码: {{ data.科技平台服务资源标识}}</p>
                                    <p>最近发布日期:{{data.科技平台服务最近发布日期 }}</p>
                                    <p>服务方信息:{{ data.科技平台服务服务方信息 }}</p>
                                    <p>访问限制:{{ data.科技平台服务访问限制 }}</p>
                                </a-card>
                            </a-col>
                        </a-row>
                    </div>
                </a-layout-content>
            </a-layout-content>
        </a-layout-content>
    </a-layout>
</template>
<script>
import { LoadingOutlined,PlusOutlined,VerticalAlignTopOutlined,DatabaseOutlined} from '@ant-design/icons-vue';
import canvass from './bcg1.vue'
export default{
    components:{
        canvass,LoadingOutlined,PlusOutlined,VerticalAlignTopOutlined,DatabaseOutlined
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
            resp:[
                {
                    "科技平台服务最近发布日期": "2022-10-25",
                    "科技平台服务资源标识": "bri",
                    "科技平台服务资源名称": "欧拉",
                    "科技平台服务服务方信息": 1,
                    "科技平台服务内容": "中国最好的科技平台",
                    "科技平台服务访问限制": "1"
                },
                {
                    "科技平台服务最近发布日期": "2021-10-25",
                    "科技平台服务资源标识": "gl",
                    "科技平台服务资源名称": "工作室",
                    "科技平台服务服务方信息": 1,
                    "科技平台服务内容": "闪闪发亮的科技平台",
                    "科技平台服务访问限制": "0"
                },
                {
                    "科技平台服务最近发布日期": "2021-10-25",
                    "科技平台服务资源标识": "gl",
                    "科技平台服务资源名称": "工作室",
                    "科技平台服务服务方信息": 1,
                    "科技平台服务内容": "闪闪发亮的科技平台",
                    "科技平台服务访问限制": "0"
                },
                {
                    "科技平台服务最近发布日期": "2021-10-25",
                    "科技平台服务资源标识": "gl",
                    "科技平台服务资源名称": "工作室",
                    "科技平台服务服务方信息": 1,
                    "科技平台服务内容": "闪闪发亮的科技平台",
                    "科技平台服务访问限制": "0"
                },
                {
                    "科技平台服务最近发布日期": "2021-10-25",
                    "科技平台服务资源标识": "gl",
                    "科技平台服务资源名称": "工作室",
                    "科技平台服务服务方信息": 1,
                    "科技平台服务内容": "闪闪发亮的科技平台",
                    "科技平台服务访问限制": "0"
                }
            ],
            shift:true
        }
    },
    methods:{
        onSubmit(){
            var form = new FormData()
            form.append("id1",this.newSubmit.id1)
            form.append("id2",this.newSubmit.id2)
            form.append("file2",this.newSubmit.file2)
            form.append("file1",this.newSubmit.file1)
            form.append("table_name",this.newSubmit.table_name)
            // this.showResults()//测试
            this.$axios.post('http://localhost:8121/heterogeneous/postgresql/exchange',form)
            .then(res=>{
                console.log(res)
                if(res.status==200){
                    this.resp=res.data.data
                    this.showResults()
                }
            }).catch(err=>{
                console.log(err)
            })
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
        showResults(){
            this.shift=false
        }
    }
}
</script>
<style scoped>
.top{
    position:fixed;
    right: 0;
    left:0;
    bottom: 0%;
    height:94%;
    z-index: 999;
}
.wholecards{
    border-radius: 5%;
}
.wholecards:hover{
    box-shadow: 0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23);
}

p{
    text-align: left;
}
.precfg{
    padding: 24px 0; 
    background: rgba(255, 255, 255,0.6);
    height: 86%;width: 80%;left: 10%;position: absolute;
}
.results{
    position:absolute;height: 80%;top:18%;bottom: 0%;width: 90%;left:5%;overflow: hidden; 
}
.aftercfg{
    padding: 24px 0; 
    background: rgba(255, 255, 255,0.6) linear-gradient(90deg, #488cba, #909ff3, #b375dc);
    height: 86%;width: 80%;left: 10%;position: absolute;
}
</style>