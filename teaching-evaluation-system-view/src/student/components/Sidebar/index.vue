<template>
  <div>
    <logo />
    <score />
    <datamanager />
  </div>
</template>

<script>
import logo from './Logo'
import score from './Score'
import datamanager from './DataManager'
export default {
  components: { logo, score, datamanager }
}
</script>
