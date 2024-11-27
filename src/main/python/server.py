from fastapi import FastAPI
from fastapi.responses import FileResponse
from fastapi.exceptions import HTTPException
import uvicorn
import logging
from pathlib import Path

app = FastAPI(title= 'API Admin',
              description= 'Analisis de ventas',
              version='1.0.0')

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

UPLOAD_FOLDER = Path("public")

@app.get("/")
async def ping():
    logger.info("hola")
    return "pong"

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
        config = uvicorn.Config(f"{__name__}:app",port=8083, host='192.168.1.35')
        server = uvicorn.Server(config)
        server.run()
    except Exception as ex:  
        print(f"error: {ex}")
    # ---------------------------------------- 
    # uvicorn.run("server:app")
    # Estar en la carpeta anterior al Script (SRC)
    # PARA ACTIVARLO PONER EN LA TERMINAL: 
    # python -m uvicorn server:app --reload --port 1234 --host '10.150.162.131'