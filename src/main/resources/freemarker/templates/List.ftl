<template>
  <div class="app-container">
      <${add_component}  @addSaveTodo="addSaveTodo"></${add_component}>   <!-- fuck 新增组件 -->
      <${edit_component} @addSaveTodo="addSaveTodo"></${edit_component}>    <!-- fuck 编辑组件 -->

      <br> <br>
<!--查询条件 总栏-->
<el-form :model="${model_name}" ref="${model_name}" :inline="true" class="demo-form-inline" > <!-- fuck model 对象名 -->
    <!--表单栏-->
    <div align="center" >
        <el-form-item prop="${queryBar.input.form_prop}" label="${queryBar.input.form_label}：">
            <el-input  placeholder="${queryBar.input.form_placeholder}" v-model="${model_name}.${queryBar.input.form_prop}"></el-input>
        </el-form-item>

        <el-form-item prop="${queryBar.datepick.form_prop}" label="${queryBar.datepick.form_label}：">
            <el-date-picker placeholder="${queryBar.datepick.form_placeholder}"  v-model="${model_name}.${queryBar.datepick.form_prop}" type="date" :editable=false value-format="yyyy-MM-dd"> </el-date-picker>
        </el-form-item>

        <el-form-item prop="${queryBar.select.form_prop}" label="${queryBar.select.form_label}：">
            <el-select  align="center"  v-model="${model_name}.${queryBar.select.form_prop}"  placeholder="${queryBar.select.form_placeholder}">
                <el-option align="center"  label="男" value="1"></el-option>
                <el-option align="center"  label="女" value="0"></el-option>
            </el-select>
        </el-form-item>
    </div>

    <!--按钮栏-->
    <div align="center" >
        <el-form-item>
            <el-button type="warning" icon="el-icon-circle-plus-outline" v-on:click="$store.state.dialog_store.add_show=true" >新增</el-button>
            <el-button type="primary" icon="el-icon-search" >查询</el-button>  <!--@click="queryList"-->
            <el-button type="danger" icon="el-icon-delete" v-on:click="batchDelete" :disabled="this.multipleSelection.length === 0">批量删除</el-button>
            <el-button  type="info" v-on:click="resetForm('${model_name}')">重置</el-button>
        </el-form-item>

    </div>
</el-form>
  </div>

</template>
<script>

</script>


