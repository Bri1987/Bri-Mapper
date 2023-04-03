<template>
    <div>
        <a-layout style="background: rgba(255, 255, 255, 0);">
            <a-layout-header style="background: rgba(255, 255, 255, 0);">
                <a-typography-title :level="3" style="text-align: left;color:#1da57a;text-align: center;">请先进行搜索前配置</a-typography-title>
                <a-divider style="border-color: #7cb305"/>
            </a-layout-header>
            <a-layout-content style="position:fixed;left:420px;top:200px;font-size: larger;font-weight: 550;" v-if="visiable">
            <br><br>
            <a-form :model="config">
                <a-row :gutter="48">
                    <a-col :span="100">
                        <a-form-item label="第一个文件id" style="margin-bottom: 60px;">
                            <a-input v-model:value="config.id1"/>
                        </a-form-item>
                        <br><br>
                        <a-form-item label="上传的第一个文件">
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
                            <vertical-align-top-outlined v-if="done1"/>{{ config.file1.name}}
                        </a-form-item>
                    </a-col>
                    <a-col :span="100">
                        <a-form-item label="第二个文件id" style="margin-bottom: 60px;">
                            <a-input v-model:value="config.id2"/>
                        </a-form-item>
                        <br><br>
                        <a-form-item label="上传的第二个文件">
                            <a-upload
                            :file-list="filelist2"
                            name="second"
                            list-type="picture-card"
                            class="second"   
                            @change="handleChange2"
                            :show-upload-list="false"
                            :customRequest="file=>uploadForm2(file)"
                            maxCount="1"
                            accept=".xml"
                            >
                                <div>
                                    <LoadingOutlined v-if="loading"></LoadingOutlined>
                                    <plus-outlined v-else>+</plus-outlined>
                                    <div class="ant-upload-text">Upload</div>
                                </div>
                            </a-upload>
                            <vertical-align-top-outlined v-if="done2"/>{{ config.file2.name}}
                        </a-form-item>
                    </a-col>
                </a-row>
                <a-form-item :wrapper-col="{ offset: 8, span: 16 }" style="position:fixed;bottom: 15%;right:20%">
                    <a-button type="primary" @click="onSubmit">Submit</a-button>
                </a-form-item>
            </a-form>
        </a-layout-content>
        <a-layout-content style="position:fixed;left:400px;top:200px;font-size: larger;font-weight: 550;" v-else>
            <a-result
                style="text-align: center;"
                status="success"
                title="成功实现配置!"
                sub-title="Preconfiguration was successfully achieved.Now you can start a unified search.Please click the button below."
            >
                <template #extra>
                    <br>
                    <a-button @click="gotoSelect" type="primary">开始统一搜索</a-button>
                </template>
            </a-result>  
        </a-layout-content>
        </a-layout>
    </div>
</template>

<script>
import { LoadingOutlined,PlusOutlined,VerticalAlignTopOutlined} from '@ant-design/icons-vue';

export default{
    data(){
        return{
            visiable:true,
            config:{
                id1:null,
                id2:null,
                file1:{},
                file2:{}
            },
            loading:false,
            done1:false,
            done2:false,
        }
    },
    components:{
        PlusOutlined,
        LoadingOutlined,
        VerticalAlignTopOutlined
    },
    methods:{
        onSubmit(){
            // this.visiable=false
            console.log(this.config)
            var form= new FormData()
            form.append("id1",this.config.id1)
            form.append("id2",this.config.id2)
            form.append("file2",this.config.file2)
            form.append("file1",this.config.file1)

            console.log(form)
            
            this.$axios.post('http://localhost:8121/function/import',form)
            .then(res=>{
                console.log(res)
                if(res.status==200){
                    //这里翻个烟花？？？
                    this.visiable=false
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        uploadForm1(filelist1){
            this.config.file1=filelist1.file
        },
        uploadForm2(filelist2){
            this.config.file2=filelist2.file
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
        gotoSelect(){
            this.$router.push('/HDC/unified')
        }
    }
}
</script>