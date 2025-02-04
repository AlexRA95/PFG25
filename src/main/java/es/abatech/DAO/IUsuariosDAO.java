package es.abatech.DAO;

import es.abatech.beans.Usuario;

import java.sql.Timestamp;

/**
 * Interfaz para las operaciones de acceso a datos relacionadas con los usuarios.
 */
public interface IUsuariosDAO {

    /**
     * Crea un nuevo usuario.
     *
     * @param usuario el usuario a crear
     */
    public void createUsuario(Usuario usuario);

    /**
     * Recupera un usuario por su correo electr&oacute;nico y contrase&ntilde;a.
     *
     * @param email el correo electr&oacute;nico del usuario
     * @param password la contrase&ntilde;a del usuario
     * @return el usuario que coincide con el correo electr&oacute;nico y la contrase&ntilde;a dados
     */
    public Usuario getUsusarioByEmailPassword(String email, String password);

    /**
     * Actualiza la &uacute;ltima conexi&oacute;n de un usuario.
     *
     * @param ultimaConexion la nueva fecha y hora de la &uacute;ltima conexi&oacute;n
     * @param idUsuario el ID del usuario cuya &uacute;ltima conexi&oacute;n se actualiza
     */
    public void updateUltimaConex(Timestamp ultimaConexion, Short idUsuario);

    /**
     * Actualiza la informaci&oacute;n general de un usuario.
     *
     * @param usuario el usuario con la informaci&oacute;n actualizada
     */
    public void updateUsuarioGen(Usuario usuario);

    /**
     * Actualiza la contrase&ntilde;a de un usuario.
     *
     * @param usuario el usuario con la nueva contrase&ntilde;a
     */
    public void updateUsuarioContra(Usuario usuario);

    /**
     * Actualiza el avatar de un usuario.
     *
     * @param usuario el usuario con el nuevo avatar
     */
    public void updateUsuarioAvatar(Usuario usuario);

    /**
     * Verifica si un correo electr&oacute;nico ya est&aacute; en uso.
     *
     * @param email el correo electr&oacute;nico a verificar
     * @return true si el correo electr&oacute;nico ya est&aacute; en uso, false en caso contrario
     */
    public Boolean correoEnUso(String email);

    /**
     * Cierra la conexi&oacute;n con la base de datos.
     */
    public void closeConnection();
}