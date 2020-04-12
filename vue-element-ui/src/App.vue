<template>
  <div id="app">
  <!--<Car/>-->
<router-view></router-view>
  </div>
</template>

<script>
  import Car from './components/Car'
export default {
  name: 'App',
  components:{Car}
}
</script>

<style>
#app {
  /*font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
 text-align: center;
  color: #2c3e50;
  margin-top: 60px;*/
}
</style>
