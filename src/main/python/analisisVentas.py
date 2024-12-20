from datetime import datetime

import sqlalchemy
from sqlalchemy import create_engine, text
from sqlalchemy.engine import Engine

import pandas as pd
import seaborn as sns
import matplotlib as mpl
mpl.use('Agg')
import matplotlib.pyplot as plt
import time


basePath = '/home/lubuntu/public/'
#basePath = 'public/'

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



def ventasVendedorPorPeriodo(fecha_inicio: str = None, fecha_fin: str = None, idVendedor: int = None, nombreVendedor: str = None):

    # Conexion a la db
    try:
        engine = get_sql_conection_engine()
    except sqlalchemy.exc.SQLAlchemyError as e:
        return e

    # Query traer ventas de un vendedor en un periodo
    try:
        query = f'''
            SELECT 
                DATE(fecha_venta) AS fecha,
                COUNT(*) AS ventas
            FROM 
                admin.ventas 
            WHERE 
                fecha_venta >= '{fecha_inicio}' 
                AND fecha_venta <= '{fecha_fin}'
                AND id_vendedor = {idVendedor}
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
        return e

    # Generar Histograma con Seaborn
    try:
        plt.figure(figsize=(10, 6))
        sns.barplot(data=df_data_sql, x="fecha", y="ventas", hue="fecha", palette="viridis", legend=False)
        plt.xlabel("Fecha")
        plt.ylabel("Ventas")
        plt.title(f"Ventas por día para el vendedor {nombreVendedor}")
        plt.xticks(rotation=45)
        plt.tight_layout()

        timestamp = time.time()
        fileName = f'img-{fecha_fin}_to_{fecha_fin}-from_id_{idVendedor}_name_{nombreVendedor}-time_{timestamp}.png'
        fileName = fileName.replace(' ', '_')
        fileName = fileName.replace(':', '-')

        plt.savefig(basePath + f'{fileName}')
        
        #plt.show()
    except Exception as e:
        return e

    return f"https://pythonadmin.lunahri.net.ar/get-photo/{fileName}"


def ventasPorVendedorPorPeriodo(fecha_inicio: str = None, fecha_fin: str = None):

    # Conexion a la db
    try:
        engine = get_sql_conection_engine()
    except sqlalchemy.exc.SQLAlchemyError as e:
        return e

    # Query traer ventas de un vendedor en un periodo
    try:
        query = f'''
            SELECT 
                id_vendedor,
                SUM(ventas) AS ventas
            FROM (
                SELECT 
                    DATE(fecha_venta) AS fecha,
                    id_vendedor,
                    COUNT(*) AS ventas
                FROM 
                    admin.ventas
                WHERE 
                    fecha_venta >= '{fecha_inicio}' 
                    AND fecha_venta <= '{fecha_fin}'
                GROUP BY 
                    DATE(fecha_venta),
                    id_vendedor
            ) AS subquery
            GROUP BY 
                id_vendedor
            ORDER BY 
                id_vendedor
        '''
        df_data_sql = pd.read_sql(query, engine)
        print(df_data_sql)
        
        #Cerrar Conexion DB
        if engine:
            engine.dispose()
            print("conexion cerrada")

    except Exception as e:
        return e

    # Generar Histograma con Seaborn
    try:

        plt.figure(figsize=(10, 6))
        sns.barplot(data=df_data_sql, x="id_vendedor", y="ventas", hue="id_vendedor", palette="coolwarm", legend=False)
        plt.xlabel("Vendedor")
        plt.ylabel("Ventas Totales")
        plt.title(f"Ventas Totales por Vendedor")
        plt.xticks(rotation=45)
        plt.tight_layout()

        timestamp = time.time()
        fileName = f'img-{fecha_fin}_to_{fecha_fin}-from_vendedores-time_{timestamp}.png'
        fileName = fileName.replace(' ', '_')
        fileName = fileName.replace(':', '-')

        plt.savefig(basePath + f'{fileName}')
        
        #plt.show()
    except Exception as e:
        return e

    return f"https://pythonadmin.lunahri.net.ar/get-photo/{fileName}"

if __name__ == '__main__':
    ventasPorVendedorPorPeriodo()