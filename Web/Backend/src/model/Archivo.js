import mongoose, { Schema } from 'mongoose';

// Esquema base
const archivoBaseSchema = new Schema({
  name: { type: String, default: "Mi Unidad" },
  type: { type: String, default: "folder" },
  owner: { type: Schema.Types.ObjectId, ref: "User", required: true }
});

const ArchivoBase = mongoose.model("Archivo", archivoBaseSchema);


// Discriminador: Archivo raíz
const archivoRaizSchema = new Schema({
  root: { type: Boolean, default: true }
});

const ArchivoRaiz = ArchivoBase.discriminator("ArchivoRaiz", archivoRaizSchema);


// Discriminador: Archivo normal
const archivoNormalSchema = new Schema({
  modified: { type: String },
  archivo: { type: Schema.Types.ObjectId, ref: "Archivo", required: true }
});

const ArchivoNormal = ArchivoBase.discriminator("ArchivoNormal", archivoNormalSchema);

// Exportación
export  { ArchivoRaiz, ArchivoNormal, ArchivoBase };
