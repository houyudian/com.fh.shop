<template>
  <div>
    <el-page-header content="汽车列表"></el-page-header>
    <el-row>
      <el-col :span="18" :push="3">
        <el-form label-width="80px">
          <el-form-item label="汽车：">
            <el-col :span="10">
              <el-input v-model="searchForm.carName"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="品牌">{{searchForm.brandId}}
            <el-col :span="10">
              <!--<el-select v-model="add.brandId" clearable placeholder="&#45;&#45;请选择&#45;&#45;">
                <el-option v-for="b in brand" :key="b.id" :label="b.brandName" :value="b.id"></el-option>
              </el-select>-->
              <brand-all :brand-id="searchForm.brandId" @changeVal="findBrandId"/>
            </el-col>
          </el-form-item>
          <el-form-item label="价格：">
            <el-col :span="5">
              <el-input prefix-icon="el-icon-money" v-model="searchForm.minPrice" placeholder="最小价格"></el-input>
            </el-col>
            <el-col :span="5">
              <el-input prefix-icon="el-icon-money" v-model="searchForm.maxPrice" placeholder="最大价格"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="地区">{{areaIdList}} {{areaNameList}}
            <el-col :span="24">
              <template v-for="(area,index) in areaListArr">
                <el-select v-model="areaIdList[index]" @focus="pos=index" @click="clearSelect()"
                           style="margin: 5px" clearable placeholder="--请选择--" :key="index"  @change="initAreaList">
                  <el-option v-for="a in area" :key="a.id" :label="a.areaName" :value="a.id+''"></el-option>
                </el-select>
              </template>
            </el-col>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-check" @click="search()">搜索</el-button>
            <el-button icon="el-icon-refresh" @click="reset()">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20">
        <el-button type="primary" icon="el-icon-plus" round @click="showAdd()">添加</el-button>
        <el-button type="primary" icon="el-icon-plus" round @click="batchAdd()">批量增加</el-button>
        <el-button type="danger" icon="el-icon-delete" round @click="batchDelete()">批量删除</el-button>
        <el-button @click="toAdd()" type="primary" size="small" icon="el-icon-plus" round>添加跳转</el-button>
      </el-col>
      <el-table :data="carList" style="width: 100%" @selection-change="handleSelectionChange"
                ref="carTable" @row-click="handClick" v-loading="carLoading" tooltip-effect="dark"
                :row-class-name="tableRowClassName" :row-key="getRowKey">
        <el-table-column type="selection" fixed :reserve-selection="true" @click="toggleSelection()"
                         width="55"></el-table-column>

        <el-table-column prop="id" label="id" fixed></el-table-column>
        <el-table-column prop="carName" label="名称" fixed></el-table-column>
        <el-table-column prop="areaName" label="地址" fixed></el-table-column>
        <el-table-column prop="brandName" label="品牌"></el-table-column>
        <el-table-column prop="price" label="价格"></el-table-column>
        <el-table-column prop="stock" label="库存"></el-table-column>
        <el-table-column prop="isHot" label="热销">
          <template slot-scope="scope">
            <el-button round type="default" icon="el-icon-sunset" size="small" v-if="scope.row.isHot==0">非热销</el-button>
            <el-button round type="danger" icon="el-icon-sunny" size="small" v-if="scope.row.isHot==1">热销</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="是否上架">
          <template slot-scope="scope">
            <el-button round type="default" icon="el-icon-download" size="small" v-if="scope.row.status==0">下架
            </el-button>
            <el-button round type="success" icon="el-icon-update2" size="small" v-if="scope.row.status==1">上架
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="500" fixed="right">
          <template slot-scope="scope">
            <el-button round type="default" @click.stop="updateHot(scope.row.id,0)" icon="el-icon-sunset" size="small"
                       v-if="scope.row.isHot==1">非热销
            </el-button>
            <el-button round type="danger" @click.stop="updateHot(scope.row.id,1)" icon="el-icon-sunny" size="small"
                       v-if="scope.row.isHot==0">热销
            </el-button>

            <el-button round type="default" @click.stop="updateStatus(scope.row.id,0)" icon="el-icon-download"
                       size="small"
                       v-if="scope.row.status==1">下架
            </el-button>
            <el-button round type="success" @click.stop="updateStatus(scope.row.id,1)" icon="el-icon-update2"
                       size="small"
                       v-if="scope.row.status==0">上架
            </el-button>

            <el-button round @click.stop="toUpdate(scope.row)" type="warning" icon="el-icon-edit" size="small">编辑
            </el-button>
            <el-button round @click.stop="deleteCar(scope.row)" type="danger" size="small" icon="el-icon-delete">删除
            </el-button>
            <el-button round @click.stop="toEdit(scope.row.id)" type="warning" size="small" icon="el-icon-edit">
              修改跳转
            </el-button>

          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="sizes,total,prev, pager, next,jumper" :current-page.sync="currPage"
                     @current-change="changePage" @size-change="changeSize" :total="totalSize"
                     :page-sizes="[5,10,15,20]" :page-size="pageSizeInfo"></el-pagination>
    </el-row>


    <el-dialog title="批量增加汽车" :visible.sync="dialogShowBatchAdd">
      <el-button type="primary" icon="el-icon-plus" @click="showAddCar()">添加</el-button>

      <template v-for="(item,index) in addFormList">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>卡片名</span>
            <el-button style="float: right;padding: 3px 0" @click="deleteCarCard(index)" type="text" v-if="index>0">删除
            </el-button>
          </div>

          <el-form label-width="80px">

            <el-form-item label="名称">
              <el-col :span="10">
                <el-input v-model="item.carName" placeholder="名称"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="价格">
              <el-col :span="10">
                <el-input prefix-icon="el-icon-money" v-model="item.price" placeholder="价格"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="库存">
              <el-col :span="10">
                <el-input v-model="item.stock" placeholder="库存"></el-input>
              </el-col>
            </el-form-item>

          </el-form>
        </el-card>
      </template>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogShowBatchAdd = false">取 消</el-button>
        <el-button type="primary" @click="addBatchCar()">确 定</el-button>
      </div>

    </el-dialog>

    <el-dialog title="增加汽车" :visible.sync="dialogFormVisible">
      <el-form label-width="80px">

        <el-form-item label="名称">
          <el-col :span="10">
            <el-input v-model="add.carName" placeholder="名称"></el-input>
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

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCar()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改汽车" :visible.sync="editShow">
      <el-form label-width="80px">
        <el-input type="hidden" v-model="edit.id" placeholder="名称"></el-input>

        <el-form-item label="名称">
          <el-col :span="10">
            <el-input v-model="edit.carName" placeholder="名称"></el-input>
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

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editShow = false">取 消</el-button>
        <el-button type="primary" @click="updateCar()">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>
<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
<script>
  import BrandAll from './brand/BrandAll'
  export default {
    components:{BrandAll},
    name: "Car",
    data() {
      return {
        areaListArr: [],
        areaIdList: [],
        areaNameList: [],
        pos: -1,
        brand: [],
        idArr: [], carList: [], currPage: 1, totalSize: 0, pageSizeInfo: 5,
        editShow: false, carLoading: false,
        dialogFormVisible: false, dialogShowBatchAdd: false,
        add: {carName: "", price: "", stock: ""},
        edit: {id: "", carName: "", price: "", stock: ""},
        searchForm: {carName: "", minPrice: "", maxPrice: "", brandId: ""},
        addFormList: [{carName: "", price: "", stock: ""}],
      }
    },
    created() {
      this.search();
      this.initBrandList();
      this.initAreaList(0)
    },

    methods: {

      clearSelect(){
        this.areaIdList.splice(i,this.areaIdList.length);
        this.areaNameList.splice(i,this.areaNameList.length);
        this.areaListArr.splice(i+1,this.areaListArr.length)
      },
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
            if (areaList.length > 0) {
              this.areaListArr.push(areaList);
            }
          }
        })
      },
      findBrandId(value){
        this.searchForm.brandId=value;
      },
      updateHot(id, hot) {
        var car = {};
        car.id = id;
        car.isHot = hot;
        this.$axios.put("http://localhost:8082/cars/hot", car).then((res) => {
          var result = res.data;
          if (result.code == 200) {
            this.search(this.currPage)
          }
        })
      },
      updateStatus(id, status) {
        var car = {};
        car.id = id;
        car.status = status;
        console.log(car)
        this.$axios.put("http://localhost:8082/cars/status", car).then((res) => {
          var result = res.data;
          if (result.code == 200) {
            this.search(this.currPage)
          }
        })
      },
      reset() {
        this.searchForm = {carName: "", minPrice: "", maxPrice: ""}
        this.areaIdList=[];
        this.areaNameList=[];
        this.areaListArr.splice(1,this.areaListArr.length)
      },
      toAdd() {
        this.$router.push("/car/add")
      },
      showAdd() {
        this.dialogFormVisible = true;
      },
      success() {
        this.$message({type: 'success', message: '删除成功!'});
      },
      batchAdd() {
        this.dialogShowBatchAdd = true;
      },
      showAddCar() {
        this.addFormList.push({carName: "", minPrice: "", maxPrice: ""})
      },
      toEdit(id) {//传参： //path:query//name:params// this.$router.push("/car/update")
        this.$router.push({path: "/car/update", query: {"id": id}})
      },
      getRowKey(row) {
        return row.id;
      },
      handClick(row) {
        this.$refs.carTable.toggleRowSelection(row)
      },
      deleteCarCard(i) {
        this.addFormList.splice(i, 1);
      },
      changeSize(pageSize) {
        this.pageSizeInfo = pageSize;
        this.search();
      },
      changePage(index) {
        this.currPage = index;
        this.search(index);
      },
      handleSelectionChange(val) {
        this.idArr = [];
        for (let item of val) {
          this.idArr.push(item.id);
        }
      },

      initBrandList() {
        this.$axios.get("http://localhost:8082/brand/findAllBrand").then((res) => {
          var result = res.data;
          console.log(result)
          if (result.code == 200) {
            this.brand = result.data;
          }
        })
      },
      addBatchCar() {
        this.$axios.post("http://localhost:8082/cars/addBatch", this.addFormList).then((response) => {
          var result = response.data;
          if (result.code == 200) {
            this.currPage = 1;
            this.search();
            this.dialogShowBatchAdd = false;
            this.addFormList = [{carName: "", price: "", stock: ''}]
          }

        })
      },

      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },

      batchDelete() {
        if (this.idArr.length > 0) {
          console.log(this.idArr)
          this.$confirm('确定全部删除？？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'waring'
          }).then(() => {
            this.$axios.delete("http://localhost:8082/cars/?ids=" + this.idArr.join(",")).then((response) => {
              var result = response.data;
              if (result.code == 200) {
                this.$refs.carTable.cl
                this.currPage = 1;
                this.search();
                this.success();
              }
            })
          })
        } else {
          this.$message({
            message: '请选择要删除的数据', type: 'warning'
          })
        }
      },


      search(index) {
        this.carLoading = true;
        if (!index) {
          index = 1;
        }
        var param = {};
        param.start = (index - 1) * this.pageSizeInfo;
        param.length = this.pageSizeInfo;
        param.carName = this.searchForm.carName;
        param.minPrice = this.searchForm.minPrice;
        param.maxPrice = this.searchForm.maxPrice;
        param.brandId=this.searchForm.brandId;
        param.provinceId=this.areaIdList[0]
        param.cityId=this.areaIdList[1]
        param.areaId=this.areaIdList[2]
        console.log(param)

        this.$axios.get("http://localhost:8082/cars/page", {
          params: param
        }).then((response) => {
          this.carLoading = false;
          var result = response.data;
          console.log(result)
          this.totalSize = result.data.totalSize;
          this.carList = result.data.carList;
        })
      },


      toUpdate(car) {
        this.$axios.get("http://localhost:8082/cars/" + car.id).then((response) => {
          let result = response.data;
          if (result.code == 200) {
            this.edit = result.data;
            this.editShow = true;
          }
        })
      },
      updateCar() {
        console.log(this.edit)
        this.$axios.put("http://localhost:8082/cars", this.edit).then((response) => {
          console.log(response)
          var result = response.data;
          if (result.code == 200) {
            this.editShow = false;
            this.search(this.currPage);
          }
        })
      },
      addCar() {
        this.$axios.post("http://localhost:8082/cars", this.$qs.stringify(this.add)).then((response) => {
          console.log(response)
          var result = response.data;
          if (result.code == 200) {
            this.dialogFormVisible = false;
            this.search();
            this.add = {carName: "", price: "", stock: ""};
          }
        })
      },

      deleteCar(car) {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var id = car.id;
          this.$axios.delete("http://localhost:8082/cars/" + id).then((response) => {
            var result = response.data;
            console.log(result)
            if (result.code == 200) {
              this.currPage = 1
              this.search();
              this.success();
            }
          })
        }).catch(() => {
          this.$message({type: 'info', message: '已取消删除'});
        });
      },
      tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
      }
    },

  }
</script>

<style scoped>

</style>
