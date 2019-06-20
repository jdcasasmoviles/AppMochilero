package com.jdcasas.appmochilero;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.List;
public class RecyclerAdapterLugares extends RecyclerView.Adapter<RecyclerAdapterLugares.ViewHolder> {
    Context context;
    List<Lugares> LugaresList;
    public RecyclerAdapterLugares(Context context, List<Lugares>  LugaresList) {
        this.context = context;
        this.LugaresList = LugaresList;
    }
    @Override
    //iniciaqliza la interfaz de la vista
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate( R.layout.card_view_lugares,null );
        return new ViewHolder(view);
    }
    @Override
    //Se actualiza los campos de acuerdo a la posicion
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Lugares paisajes=LugaresList.get(posicion  );
        cargaImagenes( paisajes.getImagen1(),holder.item_image1  );
        cargaImagenes( paisajes.getImagen2(),holder.item_image2  );
        holder.tv_item1.setText(paisajes.getDescripcion());
        holder.tv_item2.setText( paisajes.getCreadoPor());
    }

    public void cargaImagenes(final String linkImagen, final ImageView iv_pasisaje){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference mStorageRef = storage.getReferenceFromUrl("gs://appmochilero-1eb31.appspot.com");
        mStorageRef.child(linkImagen ).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri.toString())
                        .centerCrop()
                        .error( R.drawable.camara )
                        .into(iv_pasisaje);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return LugaresList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public int currentItem;
        public ImageView item_image1;
        public ImageView item_image2;
        public TextView tv_item1;
        public TextView tv_item2;
        public ViewHolder(View itemView) {
            super(itemView);
            item_image1 = (ImageView)itemView.findViewById(R.id.item_image1);
            item_image2= (ImageView)itemView.findViewById(R.id.item_image2);
            tv_item1 = (TextView)itemView.findViewById(R.id.tv_item1);
            tv_item2 = (TextView)itemView.findViewById(R.id.tv_item2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }
}

