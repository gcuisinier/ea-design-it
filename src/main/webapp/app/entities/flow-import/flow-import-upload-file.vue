<template>
  <div>
    <h2 id="page-heading" data-cy="FlowImportHeading">
      <span id="flow-import-heading">Flow Imports</span>
      <div class="d-flex justify-content-end" v-if="rowsLoaded || isFetching">
        <button class="btn btn-info mr-2" v-on:click="filterErrors" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Filter Errors</span>
        </button>
      </div>
    </h2>

    <div v-if="!rowsLoaded">
      <div class="form-group">
        <div class="custom-file">
          <input type="file" id="customFile" @change="handleFileUpload($event)" multiple />
          <label class="custom-file-label" for="customFile">{{ excelFileNames }}</label>
        </div>
      </div>
      <div class="form-group" v-if="excelFiles">
        <button type="submit" class="btn btn-primary mb-2" v-on:click="submitFile()">Submit File</button>
      </div>
    </div>

    <br />
    <div class="alert alert-warning" v-if="!isFetching && dtos && dtos.length === 0">
      <span>No flowImports found</span>
    </div>

    <div v-for="dto in dtos" :key="dto.excelFileName">
      <h4>{{ dto.excelFileName }}</h4>

      <div class="table-responsive" v-if="dto.flowImports && dto.flowImports.length > 0">
        <table class="table table-striped" aria-describedby="dto.flowImports">
          <thead>
            <tr>
              <th scope="row"><span>Import Interface Status</span></th>
              <th scope="row"><span>Import Functional Flow Status</span></th>
              <th scope="row"><span>Import Data Flow Status</span></th>
              <th scope="row"><span>Import Status Message</span></th>
              <th scope="row"><span>ID</span></th>
              <th scope="row"><span>Id Flow From Excel</span></th>
              <th scope="row"><span>Flow Alias</span></th>
              <th scope="row"><span>Source Element</span></th>
              <th scope="row"><span>Target Element</span></th>
              <th scope="row"><span>Description</span></th>
              <th scope="row"><span>Integration Pattern</span></th>
              <th scope="row"><span>Frequency</span></th>
              <th scope="row"><span>Format</span></th>
              <th scope="row"><span>Swagger</span></th>
              <th scope="row"><span>Blueprint</span></th>
              <th scope="row"><span>Blueprint Status</span></th>
              <th scope="row"><span>Flow Status</span></th>
              <th scope="row"><span>Comment</span></th>
              <th scope="row"><span>Document Name</span></th>
              <th scope="row"><span>Existing Application ID</span></th>
              <th scope="row"></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="flowImport in dto.flowImports" :key="flowImport.idFlowFromExcel" data-cy="entityTable">
              <td>
                <span v-bind:class="[flowImport.importInterfaceStatus === 'ERROR' ? 'rederror' : '']">
                  {{ flowImport.importInterfaceStatus }}</span
                >
              </td>
              <td>
                <span v-bind:class="[flowImport.importFunctionalFlowStatus === 'ERROR' ? 'rederror' : '']">{{
                  flowImport.importFunctionalFlowStatus
                }}</span>
              </td>
              <td>
                <span v-bind:class="[flowImport.importDataFlowStatus === 'ERROR' ? 'rederror' : '']">{{
                  flowImport.importDataFlowStatus
                }}</span>
              </td>
              <td>
                <span v-bind:class="[flowImport.importStatusMessage === 'ERROR' ? 'rederror' : '']">{{
                  flowImport.importStatusMessage
                }}</span>
              </td>
              <td>
                <router-link :to="{ name: 'FlowImportView', params: { flowImportId: flowImport.id } }">{{ flowImport.id }}</router-link>
              </td>
              <td>{{ flowImport.idFlowFromExcel }}</td>
              <td>{{ flowImport.flowAlias }}</td>
              <td>{{ flowImport.sourceElement }}</td>
              <td>{{ flowImport.targetElement }}</td>
              <td>{{ flowImport.description }}</td>
              <td>{{ flowImport.integrationPattern }}</td>
              <td>{{ flowImport.frequency }}</td>
              <td>{{ flowImport.format }}</td>
              <td>{{ flowImport.swagger }}</td>
              <td>{{ flowImport.blueprint }}</td>
              <td>{{ flowImport.blueprintStatus }}</td>
              <td>{{ flowImport.flowStatus }}</td>
              <td>{{ flowImport.comment }}</td>
              <td>{{ flowImport.documentName }}</td>
              <td>{{ flowImport.existingApplicationID }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./flow-import-upload-file.component.ts"></script>
<style>
.rederror {
  background-color: red;
  color: white;
}
</style>
