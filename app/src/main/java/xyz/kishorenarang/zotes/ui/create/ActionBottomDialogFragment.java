package xyz.kishorenarang.zotes.ui.create;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import xyz.kishorenarang.zotes.R;

import static android.app.Activity.RESULT_OK;
import static android.provider.Settings.System.DATE_FORMAT;

public class ActionBottomDialogFragment extends BottomSheetDialogFragment
        implements View.OnClickListener
{

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

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
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

    @Override public void onClick(View view) {
        if(view == view.findViewById(R.id.addImageFromCamera))
        {
            //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //startActivityForResult(intent, CAMERA);
            takePicture(view);
        }
        else if(view == view.findViewById(R.id.addImageFromPhotos))
        {
            try {
                pickPictureFromGallery(view);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(view == view.findViewById(R.id.viewAttachedImages))
        {
            Intent intent = new Intent(getContext(), ViewAttachmentsActivity.class);
            intent.putExtra("File",file);
            this.startActivity(intent);
        }
        //TextView tvSelected = (TextView) view;
        //mListener.onItemClick(tvSelected.getText().toString());
        //dismiss();
    }
    public void takePicture(View view)
    {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }

    public void pickPictureFromGallery(View view) throws IOException {
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
            }
        }
        else if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                Log.e("Image Pick -> ",file.toString());
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }
}