from datetime import datetime

import sqlalchemy
from sqlalchemy import create_engine, text
from sqlalchemy.engine import Engine

import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import mpld3


def get_sql_conection_engine(server: str = None, database: str = None, username: str = None, password: str = None) -> Engine:
    try:
        connection_url = sqlalchemy.URL.create(
            drivername='mysql+mysqlconnector',
            username='root',
            password='root',
            host='192.168.1.42',
            port='3306',
            database='admin')

        engine = create_engine(connection_url)

        with engine.connect() as connection:
            connection.execute(text("SELECT 1 "))
            print("Conectado exitosamente a SQL Server con SQLAlchemy.")

    except sqlalchemy.exc.SQLAlchemyError as connect_err:
        error_msg = f"Error al conectar a la base de datos: {str(connect_err)}"
        print(error_msg)
        raise
    except Exception as generic_err:
        error_msg = f"Error inesperado: {str(generic_err)}"
        print(error_msg)
        raise

    return engine



def ventasVendedorPorPeriodo(fecha_inicio: datetime = None, fecha_fin: datetime = None, idVendedor: int = None):

    # Conexion a la db
    try:
        engine = get_sql_conection_engine()
    except sqlalchemy.exc.SQLAlchemyError as e:
        print(f"Error al inicializar la conexión a SQL Server: {str(e)}")
        raise

    # Query traer ventas de un vendedor en un periodo
    try:
        query = '''
            SELECT DATE(fecha_venta) AS fecha,
                    COUNT(*) as ventas
            FROM admin.ventas 
            WHERE fecha_venta >= '2024-11-01 00:00:00' 
                AND fecha_venta <= '2024-11-30 00:00:00'
                AND id_vendedor = 1
            GROUP BY 
                DATE(fecha_venta)
            ORDER BY 
	            fecha
        '''
        df_data_sql = pd.read_sql(query, engine)
        print(df_data_sql)
        
        #Cerrar Conexion DB
        if engine:
            engine.dispose()
            print("conexion cerrada")

    except Exception as e:
        print("error al Ejecutar el query")

    # Generar Histograma con Seaborn
    try:
        plt.figure(figsize=(10, 6))
        sns.barplot(data=df_data_sql, x="fecha", y="ventas", palette="viridis")
        plt.xlabel("Fecha")
        plt.ylabel("Ventas")
        plt.title(f"Ventas por día para el vendedor {idVendedor}")
        plt.xticks(rotation=45)  # Rotar etiquetas de fecha para legibilidad
        plt.tight_layout()
        plt.savefig('public/file.png')
        #plt.show()
    except Exception as e:
        print("Error al generar el histograma:", str(e))


if __name__ == '__main__':
    ventasVendedorPorPeriodo()