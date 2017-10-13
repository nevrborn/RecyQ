package com.donkeymonkey.recyq.dialogs;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.donkeymonkey.recyq.BuildConfig;
import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.Utils.PermissionUtils;
import com.donkeymonkey.recyq.activities.LoginActivity;
import com.donkeymonkey.recyq.activities.MainActivity;
import com.donkeymonkey.recyq.model.Coupon;
import com.donkeymonkey.recyq.model.Coupons;
import com.donkeymonkey.recyq.model.User;
import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class DialogProfile extends DialogFragment {

    private static final int REQUEST_PERMISSIONS = 1000;
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    private User mUser;
    private Coupons mCoupons;

    private ImageView mImageView;
    private TextView mName;
    private TextView mUsersCoupons;
    private RecyclerView mRecyclerView;
    private CouponsAdapter mCouponsAdapter;

    private FirebaseAuth mAuth;

    private String mCurrentPhotoPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_profile, null);

        PermissionUtils.requestIfNeeded(getActivity(), PERMISSIONS, REQUEST_PERMISSIONS);

        mUser = User.getInstance();
        mCoupons = Coupons.getInstance();

        mImageView = (ImageView) view.findViewById(R.id.profile_imageview);
        mName = (TextView) view.findViewById(R.id.profile_textview_username);
        mUsersCoupons = (TextView) view.findViewById(R.id.profile_textview_coupons);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.profile_recyclerview);


        // Setting fonts
        Typeface pt_mono_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono.ttf");
        Typeface pt_mono_bold_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono_bold.ttf");
        Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");

        String firstName = "";
        String lastName = "";

        if (mUser.getName().length() != 0) firstName = mUser.getName().substring(0, 1).toUpperCase() + mUser.getName().substring(1).toLowerCase();
        if (mUser.getLastName().length() != 0) lastName = mUser.getLastName().substring(0, 1).toUpperCase() + mUser.getLastName().substring(1).toLowerCase();

        String name = firstName + " " + lastName;

        mName.setText(name);
        mName.setTypeface(pt_mono_bold_font);

        mUsersCoupons.setTypeface(pt_mono_font);

        if (mCoupons.getCouponsForUser(mUser.getUid()) != null) {
            mUsersCoupons.setText(R.string.profile_coupons);
        } else {
            mUsersCoupons.setText(R.string.profile_no_coupons);
        }

        if (mUser.getPhotoPath() != null) {
            mImageView.setImageURI(mUser.getPhotoPath());
        }

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        TextView mTitle = new TextView(getActivity());
        mTitle.setText(R.string.profile_title);
        mTitle.setBackgroundColor(Color.DKGRAY);
        mTitle.setPadding(10, 10, 10, 10);
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(30);
        mTitle.setTypeface(volvobroad_font);

        // set the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCouponsAdapter = new CouponsAdapter(mCoupons);
        mRecyclerView.setAdapter(mCouponsAdapter);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCustomTitle(mTitle)
                .setNeutralButton(R.string.ok, null)
                .setNeutralButton(R.string.profile_logout, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //logout();
                        mUser.setLoggingOut(true);
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.ok, null)
                .create();
    }

    private void logout() {

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.profile_logout_title)
                .setMessage(R.string.profile_logout_text)
                .setNegativeButton(R.string.ok, null)
                .setNeutralButton(R.string.profile_logout, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mUser.setLoggingOut(true);
                        dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    // MARK - Camera functions

    private void selectImage() {
        final CharSequence[] items = {getString(R.string.profile_take_photo), getString(R.string.profile_photo_gallery)};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.profile_add_photo_title));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.profile_take_photo))) {
                    cameraIntent();
                } else if (items[item].equals(getString(R.string.profile_photo_gallery))) {
                    galleryIntent();
                }
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile;
            try {
                photoFile = createImageFile();
                // Continue only if the File was successfully created
                if (photoFile != null) {

                    Uri photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_IMAGE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView
            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            String path = imageUri.getPath();

            mUser.setPhotoPath(imageUri);
            mImageView.setImageURI(imageUri);

            // ScanFile so it will appear in the device gallery
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{path}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK
                && null != data) {

            Uri selectedImage = data.getData();
            String path = selectedImage.getPath();

            mUser.setPhotoPath(selectedImage);
            mImageView.setImageURI(selectedImage);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            // Confirm required permissions have been granted
            boolean hasPermissions = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    // Cannot proceed without permissions
                    openUpSettings();
                }
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openUpSettings() {
        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString());
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    // MARK - Recycler view functions

    public class CouponsAdapter extends RecyclerView.Adapter<CouponsHolder> {

        private List<Coupon> mCouponList;

        public CouponsAdapter(Coupons coupons) {
            mCouponList = coupons.getCouponsForUser(mUser.getUid());
        }

        @Override
        public CouponsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View itemView = inflater.inflate(R.layout.viewholder_coupons, parent, false);
            return new CouponsHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CouponsHolder holder, int position) {
            Coupon coupon = mCouponList.get(position);
            holder.setCoupon(coupon);
            holder.itemView.setOnClickListener(holder);
            holder.mCouponName.setText(coupon.getCouponName());

            if (coupon.getRedeemed()) {
                holder.mRedeemed.setText(getString(R.string.profile_coupons_redeemed));
                holder.mRedeemed.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
            } else {
                holder.mRedeemed.setText(getString(R.string.profile_coupons_not_redeemed));
                holder.mRedeemed.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRedDark));
            }

        }

        @Override
        public int getItemCount() {
            return mCouponList.size();
        }


    }

    public class CouponsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Coupon mCoupon;

        private TextView mCouponName;
        private TextView mRedeemed;


        public CouponsHolder(View itemView) {
            super(itemView);

            mCouponName = (TextView) itemView.findViewById(R.id.coupon_textview_coupon_name);
            mRedeemed = (TextView) itemView.findViewById(R.id.coupon_textview_redeemed);

            // Setting fonts
            Typeface pt_mono_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono.ttf");
            Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");

            mCouponName.setTypeface(pt_mono_font);
            mRedeemed.setTypeface(pt_mono_font);

            mRedeemed.setTextSize(15);

        }

        public void setCoupon(Coupon coupon) {
            mCoupon = coupon;
        }

        @Override
        public void onClick(View view) {

            if (!mCoupon.getRedeemed()) {

                if (mCoupon.getCouponName().contains("Donatie")) {

                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.profile_coupons_donation_title)
                            .setMessage(R.string.profile_coupons_donation_text)
                            .setNegativeButton(R.string.ok, null)
                            .show();

                } else {

                    final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

                    final EditText codeInput = new EditText(getActivity());

                    codeInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                    codeInput.setHint(R.string.profile_coupons_redeem_enter_code);

                    alertDialog.setView(codeInput);
                    alertDialog.setTitle(R.string.profile_coupons_redeem_title);
                    alertDialog.setMessage(getString(R.string.profile_coupons_redeem_text));
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.profile_coupons_not_redeemed), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (!codeInput.getText().equals("")) {

                                int code = Integer.parseInt(codeInput.getText().toString());

                                if (code == mCoupon.getValidationCode()) {
                                    Toast.makeText(getActivity(), R.string.profile_coupons_redeem_success_text, Toast.LENGTH_SHORT).show();

                                    DatabaseReference mCouponRef = FirebaseDatabase.getInstance().getReference("coupons");
                                    mCouponRef.child(mCoupon.getUid()).child("redeemed").setValue(true);

                                    dismiss();

                                } else {
                                    Toast.makeText(getActivity(), R.string.profile_coupons_redeem_error_text, Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                    alertDialog.show();
                }
            } else {
                Toast.makeText(getActivity(), R.string.profile_coupons_already_redeemed, Toast.LENGTH_SHORT).show();
            }



        }

    }
}
