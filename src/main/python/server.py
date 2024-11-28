from fastapi import FastAPI
from fastapi.responses import FileResponse
from fastapi.exceptions import HTTPException
import uvicorn
import logging
from pathlib import Path
from pydantic import BaseModel
import analisisVentas

app = FastAPI(title= 'API Admin',
              description= 'Analisis de ventas',
              version='1.0.0')

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

UPLOAD_FOLDER = Path("/home/lubuntu/public")
#UPLOAD_FOLDER = Path("public")

class ResponseModel(BaseModel):
    url: str | None
    exception: str | None


#Docs: https://pythonadmin.lunahri.net.ar/docs

#example: https://pythonadmin.lunahri.net.ar/
@app.get("/")
async def ping():
    logger.info("hola")
    return "pong"


#example: https://pythonadmin.lunahri.net.ar/analizarVentasVendedor?fecha_inicio=2024-11-01 00:00:00&fecha_fin=2024-12-01 00:00:00&idVendedor=1&nombreVendedor=Nombre del vendedor
@app.post("/analizarVentasVendedor")
async def analizarVentasVendedor(
    fecha_inicio: str = None, 
    fecha_fin: str = None, 
    idVendedor: int = None, 
    nombreVendedor: str = None
) -> ResponseModel:

    try:
        url = analisisVentas.ventasVendedorPorPeriodo(
            fecha_inicio, 
            fecha_fin, 
            idVendedor, 
            nombreVendedor
        )

        return ResponseModel(url=url, exception=None)
    except Exception as ex:
        return ResponseModel(url=None, exception=str(ex))


#example: https://pythonadmin.lunahri.net.ar/analizarVentasPorVendedores?fecha_inicio=2024-11-01 00:00:00&fecha_fin=2024-12-01 00:00:00
@app.post("/analizarVentasPorVendedores")
async def analizarVentasPorVendedores(
    fecha_inicio: str = None, 
    fecha_fin: str = None,
) -> ResponseModel:

    try:
        url = analisisVentas.ventasPorVendedorPorPeriodo(
            fecha_inicio, 
            fecha_fin
        )

        return ResponseModel(url=url, exception=None)
    except Exception as ex:
        return ResponseModel(url=None, exception=str(ex))


#example: https://pythonadmin.lunahri.net.ar/get-photo/
@app.get("/get-photo/{filename}")
async def get_photo(filename: str = None):
    logger.info(filename)
    file_path = UPLOAD_FOLDER / filename
    logger.info(file_path)
    if not file_path.exists() or not file_path.is_file():
        raise HTTPException(status_code=404, detail="Foto no encontrada")
    
    return FileResponse(file_path, media_type="image/png")

### Main
if __name__ == "__main__":
    # ----------------------------------------
    try:
        config = uvicorn.Config(f"{__name__}:app",port=8083, host='192.168.1.42')
        server = uvicorn.Server(config)
        server.run()
    except Exception as ex:  
        print(f"error: {ex}")
    # ---------------------------------------- 
    # uvicorn.run("server:app")
    # Estar en la carpeta anterior al Script (SRC)
    # PARA ACTIVARLO PONER EN LA TERMINAL: 
    # python -m uvicorn server:app --reload --port 1234 --host '10.150.162.131'