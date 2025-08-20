# 🔐 Initial Login Configuration

Proyecto **Spring Boot WebFlux** con autenticación basada en **JWT (JSON Web Token)**, soporte para **refresh tokens** y **logout** con invalidación de tokens.  
Está pensado como una configuración inicial para proyectos que necesiten manejar registro, login y autorización reactiva en **Java**.

---

## 📌 Características

- ✅ Registro de usuarios con credenciales encriptadas (`BCryptPasswordEncoder`)
- ✅ Autenticación con generación de **Access Token** y **Refresh Token**
- ✅ Endpoint para refrescar el `access token` sin necesidad de re-login
- ✅ Logout con invalidación del token (soporte para blacklist con Redis o DB)
- ✅ Integración con **Spring Security + WebFlux**
- ✅ Persistencia con **PostgreSQL (R2DBC)**

---

## 📂 Estructura principal del proyecto

