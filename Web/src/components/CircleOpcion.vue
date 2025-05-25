<template>
  <span class="circle d-flex align-items-center justify-content-center fs-5 fw-bold"
    :class="{ 'selected': opcion.seleccionada }" @click="seleccionar">
    {{ opcion.alternativa }}
  </span>
</template>

<script setup>
const props = defineProps({
  opcion: {
    type: Object,
    required: true
  }
});
const emit = defineEmits(['seleccionar']);

const seleccionar = () => {
  emit('seleccionar', props.opcion.alternativa);
}
</script>
