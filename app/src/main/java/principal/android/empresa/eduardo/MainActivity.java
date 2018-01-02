package principal.android.empresa.eduardo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import layout.Admin.actualizar_admin;
import layout.Problema.listar_problema;
import layout.Relacion.BlankFragment;
import layout.Requisito.listar_requisito;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,SearchView.OnQueryTextListener{
TextView admin,correo;
String nombadmin,nombcorreo,nomb_buscador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 21)
        {
            //limpiar barra
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //copia el color del toolbar actual de la app
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //insertar color deseado
            //getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicio();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //CAPTURAR DATOS DEL LOGIN
        SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        //comillas vacia para si hay error en el id o pass
        nombadmin = prefs.getString("ID", "");
        nombcorreo= prefs.getString("EMAIL", "");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //INGRESAR DATOS EN NAVIGATION DRAWER
        View hView = navigationView.getHeaderView(0);
        admin= (TextView) hView.findViewById(R.id.nombre);
        correo = (TextView) hView.findViewById(R.id.correo1);
        admin.setText("ADMINISTRADOR: "+nombadmin.toUpperCase());
        correo.setText(nombcorreo);

        navigationView.setNavigationItemSelectedListener(this);


    }
    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.buscador);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //permite modificar el hint que el EditText muestra por defecto
        searchView.setQueryHint("BUSCAR PROYECTOS");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "buscador", Toast.LENGTH_SHORT).show();
                //se oculta el EditText
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }


    public void cerrar_sesion(){
        //limpiar los datos almacenados para inicar sesion
        SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        Intent intent=new Intent(MainActivity.this,Loading.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();

    }
    public void inicio(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        inicio container1Fragment = new inicio();
        //animacion adelantar R.anim.enter_from_right, R.anim.exit_to_left
        fragmentManager.beginTransaction().replace(R.id.contenedor_fragments, container1Fragment).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cerrar_sesion) {

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
            dialogo1.setTitle("Importante");
            dialogo1.setMessage("¿Deseas cerrar sesion ?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    cerrar_sesion();                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                }
            });
            dialogo1.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nuevo) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            BlankFragment container1Fragment = new BlankFragment();
            //animacion adelantar R.anim.enter_from_right, R.anim.exit_to_left
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.contenedor_fragments, container1Fragment).addToBackStack(null).commit();
        } else if (id == R.id.requisito) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            listar_requisito container1Fragment = new listar_requisito();
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.contenedor_fragments, container1Fragment).addToBackStack(null).commit();

        } else if (id == R.id.problemas) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            listar_problema container1Fragment = new listar_problema();
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.contenedor_fragments, container1Fragment).addToBackStack(null).commit();


        } else if (id == R.id.modificar_admin) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            actualizar_admin container1Fragment = new actualizar_admin();
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.contenedor_fragments, container1Fragment).addToBackStack(null).commit();
        } else if (id == R.id.modificar_contra) {

        }  else if (id == R.id.cerrar_sesion) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
            dialogo1.setMessage("¿Deseas cerrar sesion ?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    cerrar_sesion();                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                }
            });
            dialogo1.show();

    }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}



