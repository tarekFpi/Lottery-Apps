package com.Rbp.world.Adapter;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.Rbp.world.Add_toCartShowActivity;
import com.Rbp.world.Helpers.MainValues;
import com.Rbp.world.LotteryActivity;
import com.Rbp.world.Model.LotteryCartShow;
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotteryCartShowAdapter extends RecyclerView.Adapter<LotteryCartShowAdapter.MyveiwHolder> {

   private List<LotteryCartShow>lotteryCartShowList;

   private Context context;
   private static int listposition=-1;

   AppSessionManager sessionManager;

   private String mainUrl = MainValues.URL;

   private String security,message,id,userId_login,point,date;

   public LotteryCartShowAdapter(List<LotteryCartShow> lotteryCartShowList, Context context) {
      this.lotteryCartShowList = lotteryCartShowList;
      this.context = context;
   }

   @Override
   public MyveiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      LayoutInflater layoutInflater=LayoutInflater.from(context);

      View view = layoutInflater.inflate(R.layout.add_to_lottery_layout,parent,false);

      return new MyveiwHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull MyveiwHolder myveiwHolder, int position) {

        LotteryCartShow item = lotteryCartShowList.get(position);

     // myveiwHolder.textView_date.setText("Up."+item.getDate());

      myveiwHolder.textView_number.setText(""+item.getNumber());

      myveiwHolder.textView_point.setText("TK."+item.getPoint());

      myveiwHolder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {


          sessionManager = new AppSessionManager(context);

            HashMap<String, String> user = sessionManager.getUserDetails();
            userId_login = user.get(AppSessionManager.KEY_SL_ID);

            String url = mainUrl + "api/remove_cart_item";

            security = Structure.SURL;

            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {

                  try {

                     JSONObject jsonObject=new JSONObject(response);
                     message=jsonObject.getString("message");

                     if(message.contains("Lotary Removed Successfully To Cart!")) {

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


                        Notification_Add(message);

                        int newPosition = myveiwHolder.getAdapterPosition();

                        lotteryCartShowList.remove(newPosition);
                        notifyItemRemoved(newPosition);

                        Intent intent2 =new Intent(context,Add_toCartShowActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent2);

                        if(lotteryCartShowList.isEmpty()){

                           Intent intent =new Intent(context,LotteryActivity.class);
                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           context.startActivity(intent);
                        }

                     }else{

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                     }
                  } catch (JSONException e) {
                     e.printStackTrace();
                  }

               }
            }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                  netWorkError(error);

               }
            }){
               @Override
               protected Map<String, String> getParams() {

                  Map<String, String> hashMap = new HashMap<String, String>();
                  hashMap.put("security_error",security);
                  hashMap.put("c_id",item.getId());
                  hashMap.put("u_id",userId_login);

                  return hashMap;
               }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

         }
      });

      setAnimiton(myveiwHolder.itemView,position);
   }

   private void Notification_Add(String message){

      if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
         NotificationChannel notificationChannel=new NotificationChannel("Channel","Channel", NotificationManager.IMPORTANCE_HIGH);
         NotificationManager manager=(NotificationManager)context.getApplicationContext().getSystemService(NotificationManager.class);
         manager.createNotificationChannel(notificationChannel);
      }
      NotificationCompat.Builder notification= new NotificationCompat.Builder(context.getApplicationContext(),"Channel")
              .setContentTitle("Remove")
              .setSmallIcon(R.drawable.ic_notifications)
              .setContentText(message)
              .setAutoCancel(true)
              //.setSound(Uri.)
              .setWhen(System.currentTimeMillis());

      NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context.getApplicationContext());
      notificationManagerCompat.notify(1,notification.build());

   }

   private void netWorkError(VolleyError error) {

      if (error instanceof NetworkError) {
         Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
      } else if (error instanceof ServerError) {
         Toast.makeText(context, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
      } else if (error instanceof AuthFailureError) {
         Toast.makeText(context, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
      } else if (error instanceof ParseError) {
         Toast.makeText(context, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
      } else if (error instanceof NoConnectionError) {
         Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show();
      } else if (error instanceof TimeoutError) {
         Toast.makeText(context, "Server time out please try again later", Toast.LENGTH_SHORT).show();
      }
      error.printStackTrace();

   }


   void setAnimiton(View viewAnimition,int position) {

      if (position > listposition) {
         ScaleAnimation animation = new ScaleAnimation(0.0f, 0.1f, 0.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                 Animation.RELATIVE_TO_SELF, 0.5f);
         animation.setDuration(1000);
         viewAnimition.startAnimation(animation);
         listposition = position;
      }
   }

   @Override
   public int getItemCount() {

      return lotteryCartShowList.size();
   }

   class MyveiwHolder extends RecyclerView.ViewHolder{

      private TextView textView_date,textView_number,textView_point;

     private FloatingActionButton  floatingActionButton;

      public MyveiwHolder(@NonNull View itemView) {
         super(itemView);

       //  textView_date=(TextView) itemView.findViewById(R.id.text_CartshowDate);

         textView_number=(TextView) itemView.findViewById(R.id.text_cartShow_number);

         textView_point=(TextView) itemView.findViewById(R.id.textCartPoint);

         floatingActionButton=(FloatingActionButton) itemView.findViewById(R.id.floation_removeCart);
      }
   }
}
