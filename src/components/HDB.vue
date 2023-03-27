<template>
    <a-layout class="top" style="position:fixed;right: 0;left:0;bottom: 0%;height:94%;z-index: 999;">
        <canvass></canvass>
        <a-layout-content style="padding: 0 50px; position:fixed;left:5%;right: 5%;bottom: 5%;top:10%;">
            <a-breadcrumb style="margin: 16px 0">
                <a-breadcrumb-item>HDB</a-breadcrumb-item>
            </a-breadcrumb>
            <a-layout-content v-if="shift" style="padding: 24px 0; background: rgba(255, 255, 255,0.6);height: 86%;width: 80%;left: 10%;position: absolute;">
                <a-page-header
                    style="text-align: center;border: 1px solid rgb(235, 237, 240);top:0%;position: absolute;width: 100%;"
                    title=">异构数据库"
                    sub-title="请先填写并提交表单"
                />
                <br><br><br><br>
                <a-form :model="newSubmit" style="position: absolute;width: 80%;left: 20%;font-weight: 600;">
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
            <a-layout-content v-else  style="padding: 24px 0; background: rgba(255, 255, 255,0.6);height: 86%;width: 80%;left: 10%;position: absolute;">
                <a-page-header
                    style="text-align: center;border: 1px solid rgb(235, 237, 240);top:0%;position: absolute;width: 100%;"
                    title=">异构数据库"
                    sub-title="查询结果"
                />
                <!--展示一堆小卡片-->
            </a-layout-content>
        </a-layout-content>
    </a-layout>
</template>
<script>
import { LoadingOutlined,PlusOutlined,VerticalAlignTopOutlined} from '@ant-design/icons-vue';
import canvass from './bcg1.vue'
export default{
    components:{
        canvass,LoadingOutlined,PlusOutlined,VerticalAlignTopOutlined
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
            resp:{},
            shift:false
        }
    },
    methods:{
        onSubmit(){
            console.log('submit!', toRaw(this.newSubmit));//这里写表单的上传方法    
            axios.post('http://localhost:8121/heterogeneous/postgresql/exchange',this.newSubmit).then(res=>{
                console.log(res)
                if(res.status==200){
                    this.resp=res.data
                    this.showResults()
                }
            }).catch(err=>{
                console.log(err)
            })
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
        showResults(){
            this.shift=false
        }
    }

}
</script>