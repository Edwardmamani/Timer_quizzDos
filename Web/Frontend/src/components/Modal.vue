<template>
    <div class="modal fade" id="btnAut" tabindex="-1" aria-labelledby="registroModalLabel" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="registroModalLabel">Modal</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body" v-if="componente">
                    <component :is="componente" />
                </div>
            </div>
        </div>
    </div>

</template>

<script setup>
import { computed } from 'vue'
import { toRef } from 'vue'


// Props
defineProps({
    componente: {
        type: [Object, Function],
        required: false
    }
})

</script>