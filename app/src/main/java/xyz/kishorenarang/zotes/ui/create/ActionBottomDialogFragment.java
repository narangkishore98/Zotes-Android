package xyz.kishorenarang.zotes.ui.create;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


import xyz.kishorenarang.zotes.LocationAddress;
import xyz.kishorenarang.zotes.MainActivity;
import xyz.kishorenarang.zotes.R;
import xyz.kishorenarang.zotes.datastore.Image;
import xyz.kishorenarang.zotes.datastore.ImageDBHelper;
import xyz.kishorenarang.zotes.datastore.Zote;
import xyz.kishorenarang.zotes.datastore.ZoteDBHelper;

import static android.app.Activity.RESULT_OK;

public class ActionBottomDialogFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {

    public View parentView;
    public Context context;


    private List<String> images = new ArrayList<String>();

    public static final String TAG = "ActionBottomDialog";
    Uri file;

    private ItemClickListener mListener;

    public static ActionBottomDialogFragment newInstance() {
        return new ActionBottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.addImageFromPhotos).setOnClickListener(this);
        view.findViewById(R.id.addImageFromCamera).setOnClickListener(this);
        view.findViewById(R.id.viewAttachedImages).setOnClickListener(this);
        view.findViewById(R.id.save).setOnClickListener(this);
        view.findViewById(R.id.discard).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view == view.findViewById(R.id.addImageFromCamera)) {
            //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //startActivityForResult(intent, CAMERA);
            takePicture(view);
        } else if (view == view.findViewById(R.id.addImageFromPhotos)) {
            pickPictureFromGallery(view);
        } else if (view == view.findViewById(R.id.save)) {
            saveZote();
        }
        else if(view == view.findViewById(R.id.discard))
        {

            EditText etTitle = parentView.findViewById(R.id.editTitle);
            EditText etContent = parentView.findViewById(R.id.editContent);
            etTitle.setText("");
            etContent.setText("");
            dismiss();
        }
        else if(view == view.findViewById(R.id.cancel))
        {
            dismiss();
        }
        //TextView tvSelected = (TextView) view;
        //mListener.onItemClick(tvSelected.getText().toString());
        //dismiss();
    }

    private void saveZote() {

        final EditText etTitle = parentView.findViewById(R.id.editTitle);
        final EditText etContent = parentView.findViewById(R.id.editContent);






            //String address  = "";
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    List<Address> addresses= null;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        String address = addresses.get(0).getLocality();
                        Zote zote = new Zote(etTitle.getText().toString(), etContent.getText().toString(), LocalDateTime.now(),address);

                        ZoteDBHelper zdbh = new ZoteDBHelper(context,null);
                        zdbh.addZote(zote);
                        int id = zdbh.getAllZotes().get(zdbh.getAllZotes().size()-1).getId();

                        Iterator<String> i = images.iterator();
                        ImageDBHelper idbh = new ImageDBHelper(context, null);
                        while(i.hasNext())
                        {
                            idbh.addImage(new Image(i.next(), zote));
                        }

                        Toast.makeText(context, "Zote Added Successfully",Toast.LENGTH_LONG).show();
                        ActionBottomDialogFragment.this.dismiss();
                    } catch (IOException e) {
                        Zote zote = new Zote(etTitle.getText().toString(), etContent.getText().toString(), LocalDateTime.now(),"Unknown");

                        ZoteDBHelper zdbh = new ZoteDBHelper(context,null);
                        zdbh.addZote(zote);
                    int id = zdbh.getAllZotes().get(zdbh.getAllZotes().size()-1).getId();

                        Iterator<String> i = images.iterator();
                        ImageDBHelper idbh = new ImageDBHelper(context, null);
                        while(i.hasNext())
                        {
                            idbh.addImage(new Image(i.next(), zote));
                        }

                        Toast.makeText(context, "Zote Added Successfully",Toast.LENGTH_LONG).show();
                        ActionBottomDialogFragment.this.dismiss();
                        e.printStackTrace();
                    }

                }
            });













    }


    public void takePicture(View view)
    {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }

    public void pickPictureFromGallery(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        file = Uri.fromFile(getOutputMediaFile());
        intent.setType("image/*"); //if we remove this code will open Google Drive selection
        intent.putExtra(Intent.ACTION_PICK, file);
        startActivityForResult(intent, 200);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Assets");
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {

                Log.e("Image Capture -> ",file.toString());
                images.add(file.toString());
            }
        }
        else if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                Log.e("Image Pick -> ",file.toString());
                images.add(file.toString());

            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }
}