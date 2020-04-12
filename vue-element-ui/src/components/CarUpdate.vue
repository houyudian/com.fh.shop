<template>
  <div>
    <h1>汽车修改</h1>
    <el-col :span="12">
      <el-form label-width="80px">
        <el-input type="hidden" v-model="edit.id"></el-input>

        <el-form-item label="名称">
          <el-col :span="10">
            <el-input v-model="edit.carName" placeholder="名称"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="品牌">{{edit.brandId}}
          <el-col :span="10">
            <!-- <el-select v-model="add.brandId" clearable placeholder="&#45;&#45;请选择&#45;&#45;">
               <el-option v-for="b in brand" :key="b.id" :label="b.brandName" :value="b.id"></el-option>
             </el-select>-->
            <brand-all :brand-id="edit.brandId" @changeVal="updateBrandId"/>
          </el-col>
        </el-form-item>
        <el-form-item label="价格">
          <el-col :span="10">
            <el-input prefix-icon="el-icon-money" v-model="edit.price" placeholder="价格"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="库存">
          <el-col :span="10">
            <el-input v-model="edit.stock" placeholder="库存"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="地区">
          <el-col :span="24">
            <el-link :underline="false" v-if="areaButton=='编辑'">{{edit.areaName}}</el-link>
            <template v-for="(area,index) in areaListArr" v-if="areaButton=='取消编辑'">
              <el-select v-model="areaIdList[index]" @focus="pos=index" @click="clearSelect()"
                         style="margin: 5px" clearable placeholder="--请选择--" :key="index" @change="initAreaList">
                <el-option v-for="a in area" :key="a.id" :label="a.areaName" :value="a.id+''"></el-option>
              </el-select>
            </template>

            <el-button type="warning" icon="el-icon-edit" @click="editArea" round>{{areaButton}}}</el-button>
          </el-col>
        </el-form-item>
        <div>
          <el-button @click="editShow = false">取 消</el-button>
          <el-button type="primary" @click="updateCar()">确 定</el-button>
        </div>
      </el-form>
    </el-col>
  </div>
</template>

<script>
  import BrandAll from './brand/BrandAll'

  export default {
    components: {BrandAll},
    name: "CarUpdate",
    data() {
      return {
        edit: {id: "", carName: "", price: "", stock: "", brandId: ""},
        brand: [],
        brandId: '',
        areaButton: "编辑",
        areaListArr: [],
        areaIdList: [],
        areaNameList: [],
        pos: -1,
      }
    },
    created() {
      this.findCar()

    },
    methods: {

      editArea() {
        if (this.areaButton == "编辑") {
          this.areaButton = "取消编辑";
          this.initAreaList(0)
        } else {
          this.areaButton = '编辑'
          this.areaIdList = [];
          this.areaNameList = [];
          this.areaListArr = [];
          this.pos = -1;
        }
      },
      initAreaList(id) {
        if (this.pos != -1) {
          this.areaListArr.splice(this.pos + 1, this.areaListArr.length)
          this.areaIdList.splice(this.pos + 1, this.areaIdList.length)
          this.areaNameList.splice(this.pos, this.areaNameList.length)

          var areaInfo = this.areaListArr[this.pos].find(x => x.id == id)
          this.areaNameList.push(areaInfo.areaName)
        }
        this.$axios.get("http://localhost:8082/area?id=" + id).then((res) => {
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
      updateBrandId(value) {
        this.edit.brandId = value
      },
      findCar() {
        var id = this.$route.query.id;
        //var id=this.$router.params.id;
        console.log(id)
        this.$axios.get("http://localhost:8082/cars/" + id).then((res) => {
          var result = res.data;
          if (result.code == 200) {
            this.edit = result.data
          }

        })
      },
      updateCar(id) {
        if (this.areaButton=='取消编辑'){
          this.edit.provinceId=this.areaIdList[0];
          this.edit.cityId=this.areaIdList[1];
          this.edit.areaId=this.areaIdList[2];
          this.edit.areaName=this.areaNameList.join("->");
        }
        this.$axios.put("http://localhost:8082/cars", this.edit).then((res) => {
          var result = res.data;
          if (result.coed == 200) {
            this.$router.push("/")
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
