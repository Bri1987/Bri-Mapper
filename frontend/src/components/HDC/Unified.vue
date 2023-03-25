<template>
    <div>
        <a-layout>
            <a-input-search
                v-model:value="select_sql"
                placeholder="input select_sql"
                enter-button="Search"
                size="large"
                @search="onSearch"
                bordered="false"
                />
            <a-layout-content style="position:inherit;height: 100%;top:0%;bottom: 0%;overflow-y: scroll;">

                
            </a-layout-content>
        </a-layout>
    </div>
</template>
<script>
import axios from 'axios';

export default{
    data(){
        return {
            select_sql:'',
            datas:{},
            length:7
        }
    },
    methods:{
        onSearch(){
            axios.post('http://localhost:8121/function/select',this.select_sql)
            .then(res=>{
                console.log(res)
                if(res.status==200)
                {
                    //展示小卡片
                    this.datas=res.data
                }
            }).catch(err=>{
                console.log(err)
            })
        }
    }
}
</script>
<style scoped>
.cards_holder{
    background-color: #fff;
    height: 100%;
    position: inherit;
    top:0%;
    bottom: 0%;

}
</style>