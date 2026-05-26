<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <!-- ===========================
         TEMPLATE PRINCIPAL
    ============================ -->
    <xsl:template match="/biblioteca">
        <html>
            <head>
                <title>Biblioteca – Informe General</title>
                <style>
                    body { font-family: Arial; margin: 20px; background: #f4f4f4; }
                    h1 { color: #333; }
                    .bloque { background: white; padding: 20px; margin-bottom: 25px; border-radius: 10px; }
                    table { width: 100%; border-collapse: collapse; margin-top: 10px; }
                    th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
                    th { background: #333; color: white; }
                </style>
            </head>

            <body>
                <h1>Biblioteca – Informe General</h1>

                <!-- BLOQUE LIBROS -->
                <div class="bloque">
                    <h2>Libros</h2>
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Título</th>
                            <th>ISBN</th>
                            <th>Editorial</th>
                            <th>Género</th>
                            <th>Copias</th>
                        </tr>
                        <!-- AQUÍ SE INSERTAN LAS FILAS -->
                        <xsl:apply-templates select="libros/libro"/>
                    </table>
                </div>

                <!-- BLOQUE AUTORES -->
                <div class="bloque">
                    <h2>Autores</h2>
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>Nacionalidad</th>
                        </tr>
                        <xsl:apply-templates select="autores/autor"/>
                    </table>
                </div>

                <!-- BLOQUE USUARIOS -->
                <div class="bloque">
                    <h2>Usuarios</h2>
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>DNI</th>
                            <th>Nombre</th>
                            <th>Teléfono</th>
                            <th>Penalización</th>
                        </tr>
                        <xsl:apply-templates select="usuarios/usuario"/>
                    </table>
                </div>

                <!-- BLOQUE PRÉSTAMOS -->
                <div class="bloque">
                    <h2>Préstamos</h2>
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Fecha alquiler</th>
                            <th>Fecha devolución</th>
                            <th>ID Ejemplar</th>
                            <th>ID Usuario</th>
                        </tr>
                        <xsl:apply-templates select="prestamos/prestamo"/>
                    </table>
                </div>

            </body>
        </html>
    </xsl:template>


    <!-- ===========================
         FILAS DE LIBROS
    ============================ -->
    <xsl:template match="libro">
        <tr>
            <td><xsl:value-of select="@id_libro"/></td>
            <td><xsl:value-of select="titulo"/></td>
            <td><xsl:value-of select="isbn"/></td>
            <td><xsl:value-of select="editorial"/></td>
            <td><xsl:value-of select="genero"/></td>
            <td><xsl:value-of select="num_copias"/></td>
        </tr>
    </xsl:template>


    <!-- ===========================
         FILAS DE AUTORES
    ============================ -->
    <xsl:template match="autor">
        <tr>
            <td><xsl:value-of select="@id_autor"/></td>
            <td><xsl:value-of select="nombre"/></td>
            <td><xsl:value-of select="apellidos"/></td>
            <td><xsl:value-of select="nacionalidad"/></td>
        </tr>
    </xsl:template>


    <!-- ===========================
         FILAS DE USUARIOS
    ============================ -->
    <xsl:template match="usuario">
        <tr>
            <td><xsl:value-of select="@id_user"/></td>
            <td><xsl:value-of select="dni_user"/></td>
            <td><xsl:value-of select="nombre_user"/></td>
            <td><xsl:value-of select="telefono_user"/></td>
            <td><xsl:value-of select="id_penalizacion"/></td>
        </tr>
    </xsl:template>


    <!-- ===========================
         FILAS DE PRÉSTAMOS
    ============================ -->
    <xsl:template match="prestamo">
        <tr>
            <td><xsl:value-of select="@id_prestamo"/></td>
            <td><xsl:value-of select="fecha_alquiler"/></td>

            <td>
                <xsl:choose>
                    <xsl:when test="string-length(fecha_devolucion) > 0">
                        <xsl:value-of select="fecha_devolucion"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <span style="color:red;">Pendiente</span>
                    </xsl:otherwise>
                </xsl:choose>
            </td>

            <td><xsl:value-of select="id_ejemplar"/></td>
            <td><xsl:value-of select="id_user"/></td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
