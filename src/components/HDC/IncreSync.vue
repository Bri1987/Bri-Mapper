<template>
    <div>
        <a-layout-header style="background: rgba(255, 255, 255, 0);">
                <a-typography-title :level="3" style="text-align: left;color:#1da57a">
                    <build-outlined />
                    增量同步
                </a-typography-title>
                <a-divider style="border-color: #7cb305" dashed />
        </a-layout-header>
        <a-layout-content style="position:fixed;left:480px;top:200px;font-size: larger;font-weight: 550;" v-if="visiable">
            <br><br>
            <a-form :model="zSubmit">
                <a-row :gutter="48">
                    <a-col :span="100" style="width: 360px;">
                        <a-form-item label="第一个文件id" style="margin-bottom: 60px;">
                            <a-input v-model:value="zSubmit.id1"/>
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
                            <vertical-align-top-outlined v-if="done1"/>{{ zSubmit.file1.name}}
                        </a-form-item>
                    </a-col>
                    <a-col :span="100" style="width: 360px;">
                        <a-form-item label="第二个文件id" style="margin-bottom: 60px;">
                            <a-input v-model:value="zSubmit.id2"/>
                        </a-form-item>
                        <a-form-item label="上传第二个文件">
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
                            <vertical-align-top-outlined v-if="done2"/>{{ zSubmit.file2.name}}
                        </a-form-item>
                    </a-col>
                </a-row>
                <a-form-item label="insert_sql" style="width: 100%;margin-bottom: 60px;">
                    <!-- <a-input v-model:value="zSubmit.insert_sql"/> -->
                    <a-textarea
                        v-model:value="zSubmit.insert_sql"
                        placeholder="insert_sql"
                        :auto-size="{ minRows: 2, maxRows: 5 }"
                        allowClear="true"
                        spellcheck="false"
                    />
                </a-form-item>
                <a-form-item :wrapper-col="{ offset: 8, span: 16 }" style="position:fixed;bottom: 12%;right:20%">
                    <a-button type="primary" @click="onSubmit">Submit</a-button>
                </a-form-item>
            </a-form>
        </a-layout-content>
        <a-layout-content style="position:fixed;left:400px;top:200px;font-size: larger;font-weight: 550;" v-else>
            <a-result
                status="success"
                title="成功实现增量同步!"
                sub-title="Incremental synchronization was successfully achieved.You can try other functions in the sider menu!"
            ></a-result>  
        </a-layout-content>
    </div>
</template>
<script>
import { LoadingOutlined,PlusOutlined,VerticalAlignTopOutlined,BuildOutlined,EditOutlined} from '@ant-design/icons-vue';
export default{
    data(){
        return {
            visiable:true,
            zSubmit:{
                insert_sql:'',
                id1:null,
                id2:null,
                file1:{},
                file2:{}
            },
            loading:false,
            done1:false,
            done2:false,
            // doggie
        }
    },
    components:{
        PlusOutlined,LoadingOutlined,VerticalAlignTopOutlined,BuildOutlined,EditOutlined
    },
    methods:{
        onSubmit(){
            var form = new FormData()
            form.append('id1',this.zSubmit.id1)
            form.append('id2',this.zSubmit.id2)
            form.append('file1',this.zSubmit.file1)
            form.append('file2',this.zSubmit.file2)
            form.append('insert_sql',this.zSubmit.insert_sql)
            this.$axios.post('http://localhost:8121/function/sync/add',form)
            .then(res=>{
                console.log(res)
                if(res.status==200){
                    this.visiable=false
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        uploadForm1(filelist1){
            this.zSubmit.file1=filelist1.file
        },
        uploadForm2(filelist2){
            this.zSubmit.file2=filelist2.file
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
        }
    }
}</script>
<style></style>