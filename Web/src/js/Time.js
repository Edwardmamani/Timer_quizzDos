function nowTime() {
    const ahora = new Date()
    const horas = ahora.getHours().toString().padStart(2, '0')
    const minutos = ahora.getMinutes().toString().padStart(2, '0')
    const segundos = ahora.getSeconds().toString().padStart(2, '0')
    return `${horas}:${minutos}:${segundos}`

}

function parseTiempo(tiempoStr) {
  // para tiempo 'MM:SS.s y tambien para 'HH:MM:SS.s'
  const [h, m, s] = tiempoStr.split(':')
  if(s) {
    const [sec, ms = 0] = s.split('.')
    return (
      parseInt(h) * 3600000 +
      parseInt(m) * 60000 +
      parseInt(sec) * 1000 +
      parseInt(ms.padEnd(3, '0')) // asegura 3 dígitos
    )
  }else {
  const [m, s] = tiempoStr.split(':')

    const [sec, ms = 0] = s.split('.')

    return (
      parseInt(m) * 60000 +
      parseInt(sec) * 1000 +
      parseInt(ms.padEnd(3, '0'))
    )
  }
  
}
  
  function formatearTiempo(ms) {
    const horas = Math.floor(ms / 3600000)
    const minutos = Math.floor((ms % 3600000) / 60000)
    const segundos = Math.floor((ms % 60000) / 1000)
    const milis = Math.floor(ms % 1000 / 100) // decimales
  
    return `${String(horas).padStart(2, '0')}:${String(minutos).padStart(2, '0')}:${String(segundos).padStart(2, '0')}.${milis}`
  }

  export {parseTiempo, formatearTiempo, nowTime}


