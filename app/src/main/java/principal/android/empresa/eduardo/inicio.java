package principal.android.empresa.eduardo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class inicio extends Fragment implements View.OnClickListener{
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2,fab3,fab4;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    public inicio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View obj=inflater.inflate(R.layout.fragment_inicio, container, false);

        fab = (FloatingActionButton)obj.findViewById(R.id.fab);
        fab1 = (FloatingActionButton)obj.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)obj.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)obj.findViewById(R.id.fab3);
        fab4 = (FloatingActionButton)obj.findViewById(R.id.fab4);
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);


        return obj;
    }



    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab4.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab4.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            //fab es el id del floating button
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                //enlace a pagina empresa
                String linkgoogle = "https://www.isagen.com.co";
                Intent intent1 = null;
                intent1 = new Intent(intent1.ACTION_VIEW, Uri.parse(linkgoogle));
                startActivity(intent1);
                break;
            case R.id.fab2:
                //enlace a facebook
                String linkfacebook = "https://www.facebook.com/IsagenEnergiaProductiva/?fref=ts";
                Intent intent2 = null;
                intent2 = new Intent(intent2.ACTION_VIEW, Uri.parse(linkfacebook));
                startActivity(intent2);
                break;
            case R.id.fab3:
                //enlace a gestion proyectos
                String linkpag = "http://gestion24.jl.serv.net.mx/mariscal24/#no-back-button";
                Intent intent3 = null;
                intent3 = new Intent(intent3.ACTION_VIEW, Uri.parse(linkpag));
                startActivity(intent3);
                break;
            case R.id.fab4:
                //enlace a youtube
                String linkyoutube = "https://www.youtube.com/user/ISAGENVideos";
                Intent intent4 = null;
                intent4 = new Intent(intent4.ACTION_VIEW, Uri.parse(linkyoutube));
                startActivity(intent4);
                break;
        }
    }


}
