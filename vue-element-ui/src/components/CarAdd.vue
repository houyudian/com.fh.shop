<template>
  <div>
    <h1>添加页面</h1>

    <el-col :span="20">
      <el-form label-width="80px">

        <el-form-item label="名称">
          <el-col :span="10">
            <el-input v-model="add.carName" placeholder="名称"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="品牌">{{add.brandId}}
          <el-col :span="10">
            <!-- <el-select v-model="add.brandId" clearable placeholder="&#45;&#45;请选择&#45;&#45;">
               <el-option v-for="b in brand" :key="b.id" :label="b.brandName" :value="b.id"></el-option>
             </el-select>-->
            <brand-all :brand-id="add.brandId" @changeVal="setBrandId"/>
          </el-col>
        </el-form-item>
        <el-form-item label="价格">
          <el-col :span="10">
            <el-input prefix-icon="el-icon-money" v-model="add.price" placeholder="价格"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="库存">
          <el-col :span="10">
            <el-input v-model="add.stock" placeholder="库存"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="地区">{{areaIdList}} {{areaNameList}}
          <el-col :span="24">
            <template v-for="(area,index) in areaListArr">
              <el-select v-model="areaIdList[index]"
                         @focus="pos=index"
                         style="margin: 5px" clearable
                         placeholder="--请选择--"
                         :key="index"  @change="initAreaList">

                <el-option v-for="a in area"
                           :key="a.id"
                           :label="a.areaName"
                           :value="a.id+''"></el-option>
              </el-select>
            </template>
          </el-col>
        </el-form-item>
        <div>
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addCar()">确 定</el-button>
        </div>
      </el-form>


    </el-col>

  </div>


</template>

<script>
  import BrandAll from './brand/BrandAll'

  export default {
    components: {BrandAll},

    name: "CarAdd",
    data() {
      return {
        add: {carName: "", price: "", stock: "", brandId: ""},
        brand: [],
        areaListArr: [],
        areaIdList: [],
        areaNameList: [],
        pos: -1,
      }
    },
    methods: {
      setBrandId(value) {
        console.log("add:" + value)
        this.add.brandId = value;
      },
      /*initBrandList(){
        this.$axios.get("http://localhost:8082/brand/findAllBrand").then((res)=>{
          var result=res.data;
          if (result.code==200){
            this.brand=result.data;
          }
        })
      },*/
      initAreaList(id) {
        if (this.pos != -1) {
          this.areaListArr.splice(this.pos + 1, this.areaListArr.length)
          this.areaIdList.splice(this.pos + 1, this.areaIdList.length)
          this.areaNameList.splice(this.pos, this.areaNameList.length)

          var areaInfo = this.areaListArr[this.pos].find(x => x.id == id)
          this.areaNameList.push(areaInfo.areaName)
        }
        this.$axios.get("http://localhost:8082/area?id="+id).then((res) => {
          var result = res.data;
          if (result.code == 200) {
            var areaList = result.data;
            console.log(areaList);
            if (areaList.length > 0) {
              this.areaListArr.push(areaList);
            }
            console.log(this.areaListArr);
          }
        })
      },
      addCar() {

        this.add.provinceId=this.areaIdList[0];
        this.add.cityId=this.areaIdList[1];
        this.add.areaId=this.areaIdList[2];
        this.add.areaName=this.areaNameList.join("->");



        this.$axios.post("http://localhost:8082/cars/info", this.add).then((res) => {
          var result = res.data;
          if (result.code == 200) {
            this.$router.push("/")
          }

        })
      }
    },
    created() {
      this.initAreaList(0)
    },
  }
</script>

<style scoped>

</style>
