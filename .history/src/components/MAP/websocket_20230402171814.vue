<template>
    <div>
        <a-layout-header style="background: rgba(255, 255, 255, 0);">
                <a-typography-title :level="3" style="text-align: left;color:#1da57a">多文件异步映射</a-typography-title>
                <a-divider style="border-color: #7cb305" dashed />
        </a-layout-header>
        <a-layout-content style="margin:0 50px;font-size: larger;font-weight: 550;" v-if="visiable">
            <br>
            <a-form :model="zSubmit">
                <a-form-item label="url" >
                    <a-input v-model:value="url"/>
                </a-form-item>
                <a-form-item :wrapper-col="{ offset: 8, span: 16 }" style="position:fixed;right:20%">
                    <a-button type="primary" @click="onWs">Submit</a-button>
                </a-form-item>

                <a-row :gutter="48">
                    <a-col :span="200">
                        <a-form-item style="color:green" label="标准数据源">
                            <a-upload
                            :file-list="filelist1"
                            :multiple="true"
                            name="first"
                            list-type="picture-card"
                            class="first"   
                            @change="handleChange1"
                            :max-count="1"
                            accept=".csv"
                            >
                                <div>
                                    <UploadOutlined v-if="loading"></UploadOutlined>
                                    <plus-outlined v-else></plus-outlined>
                                    <div class="ant-upload-text">Upload</div>
                                </div>
                            </a-upload>
                            
                        </a-form-item>
                    </a-col>
                    <a-col :span="200">
                        <a-form-item label="异构数据源">
                            <a-upload
                            :file-list="filelist2"
                            action="#"
                            name="second"
                            list-type="picture-card"
                            class="second"   
                            @change="handleChange2"
                            accept=".xml"
                            >
                                <div>
                                    <UploadOutlined v-if="loading"></UploadOutlined>
                                    <plus-outlined v-else>+</plus-outlined>
                                    <div class="ant-upload-text">Upload</div>
                                </div>
                            </a-upload>
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
                status="success"
                title="成功实现多文件异步映射!"
                sub-title="Mapping was successfully achieved!"
            ></a-result>  
        </a-layout-content>
    </div>
</template>
<script>
import { UploadOutlined,PlusOutlined,VerticalAlignTopOutlined} from '@ant-design/icons-vue';
import axios from 'axios';
import FormData from 'form-data';
import { ref } from 'vue';

export default{
    data(){
        return {
            sessionId:'',
            visiable: true,
            url:'',
            file0: {
                uid: '',
                name: '',
                status: 'done',
                response: 'Server Error 500', // custom error message to show
                url: ''
            },
            filelist0: [
                {
                uid: '',
                name: '',
                status: 'done',
                response: 'Server Error 500', // custom error message to show
                url: ''
                }
            ],
            loading:false,
            done1:false,
            done2:false,
        }
    },
    components:{
        PlusOutlined,
        UploadOutlined,
        VerticalAlignTopOutlined,
    },
    methods: {
        formatDate(now)   {    
            var   year=now.getFullYear();     
            var   month=now.getMonth()+1;     
            var   date=now.getDate();     
            var   hour=now.getHours();     
            var   minute=now.getMinutes();     
            var   second=now.getSeconds();     
            return   year+"-"+(month=month<10?("0"+month):month)+"-"+(date=date<10?("0"+date):date)+" "+(hour=hour<10?("0"+hour):hour)+":"+(minute=minute<10?("0"+minute):minute)+":"+(second=second<10?("0"+second):second);     
        } ,
        onWs() {
            console.log(this.url);
            const ws = new WebSocket(this.url);
            console.log(ws);
            ws.addEventListener('open', e => {
                console.log('长连接连接成功')
                // 连接成功后的回调函数，连接成功后一般需要开启心跳方法
                // clearTimeout(timer.reconnectObj)
                // dispatch('wsHeartStart')
            });

            ws.addEventListener('message', e => {
                
                const date =this.formatDate(new Date())
                console.log('服务端回应' + date + e.data);
                this.sessionId = e.data.value;
                // 接收服务端数据时触发的回调函数
                // dispatch('wsHearReset')
                // dispatch('handlerWSMessage', e)
            });

            ws.addEventListener('close', e => {
                console.log('WebSocket已断开')
                // 连接关闭时触发
                // dispatch('handlerWSClose', e)
            });

            ws.addEventListener('error', e => {
                console.log('WebSocket发生错误')
                // 通信发生错误时触发
                // dispatch('handlerWSError', e)
            });
        },
        onSubmit() {

            
            // this.visiable=false
            // console.log('submit!', toRaw(this.zSubmit));//这里写表单的上传方法    
            // axios.post('http://localhost:8121/function/sync/add',this.zSubmit)
            // .then(res=>{
            //     console.log(res)
            //     if(res.status==200){
            //         //这里翻个烟花？？？
            //         this.visiable=false
            //     }
            // }).catch(err=>{
            //     console.log(err)
            // })
        },
        handleChange1 = info => {
            if (info.file.status === 'uploading') {
                this.loading = true;
                return;
            }
        },
        // handleChange1(info){
        //     console.log("handleChange1", info.file.name)
        //     let resFileList = [...info.fileList];
        //     this.fileList1 = resFileList;
        //     console.log(info.file.status);
        //     if (info / file.status === 'done') {
        //         console.log(this.fileList1);
        //     }
            
            // if(info.file.status==='uploading') {
            //     loading = true;
            //     return; 
            // }
        },
        handleChange2(info){
            console.log("handleChange2",info.file.name)
            if(info.file.status==='uploading') {
                loading = true;
                return; 
            }
        }
    }
}
</script>
<style></style>