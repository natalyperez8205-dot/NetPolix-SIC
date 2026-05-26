package dao;

import conexion.ConexionBD;
import modelo.Idioma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Idioma_Dao {

    public void guardarIdioma(
            Idioma idioma){

        try{

            Connection con =
                    ConexionBD.getConexion();

            String sql =
                    "INSERT INTO idioma(nombre) VALUES(?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    idioma.getNombre());

            ps.executeUpdate();

            System.out.println(
                    "Idioma guardado");

        } catch(Exception e){

            System.out.println(
                    e.getMessage());
        }
    }

    public List<Idioma> listarIdiomas(){

        List<Idioma> lista =
                new ArrayList<>();

        try{

            Connection con =
                    ConexionBD.getConexion();

            String sql =
                    "SELECT * FROM idioma";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()){

                Idioma idioma =
                        new Idioma();

                idioma.setId(
                        rs.getInt("id"));

                idioma.setNombre(
                        rs.getString("nombre"));

                lista.add(idioma);
            }

        } catch(Exception e){

            System.out.println(
                    e.getMessage());
        }

        return lista;
    }

    public void eliminarIdioma(int id){

        try{

            Connection con =
                    ConexionBD.getConexion();

            String sql =
                    "DELETE FROM idioma WHERE id = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println(
                    "Idioma eliminado");

        } catch(Exception e){

            System.out.println(
                    e.getMessage());
        }
    }
}
