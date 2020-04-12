<template>
  <div>
    <el-select v-model="bradIdInfo" clearable placeholder="--请选择--">
      <el-option v-for="b in brand"
                 :key="b.id"
                 :label="b.brandName"
                 :value="b.id+''">
      </el-option>
    </el-select>
  </div>
</template>

<script>
  export default {
    name: "BrandAll",
    /*props: {brandId: String,},*/
    computed: {
      bradIdInfo: {
        get() {
          return this.brandId
        },
        set(v) {
          console.log(v)
          this.$emit("changeVal",v)
        },
      }
    },
    data() {
      return {
        brand: [],
        brandId: ""
      }
    },
    methods: {
      initBrandList() {
        this.$axios.get("http://localhost:8082/brand/findAllBrand").then((res) => {
          var result = res.data;
          if (result.code == 200) {
            this.brand = result.data;
          }
        })
      },
    },
    created() {
      this.initBrandList()
    },
  }
</script>

<style scoped>

</style>
