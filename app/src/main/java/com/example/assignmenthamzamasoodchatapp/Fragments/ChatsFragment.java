package com.example.assignmenthamzamasoodchatapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmenthamzamasoodchatapp.Adapter.UserAdapter;
import com.example.assignmenthamzamasoodchatapp.Model.Chat;
import com.example.assignmenthamzamasoodchatapp.Model.User;
import com.example.assignmenthamzamasoodchatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser fuser;
    FirebaseFirestore reference;

    private List<String> usersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        reference =FirebaseFirestore.getInstance();

        reference.collection("chats").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Chat chat=document.toObject(Chat.class);
                        if(chat.getSender().equals((fuser.getUid()))){
                            usersList.add(chat.getReceiver());
                        }
                        if(chat.getReceiver().equals(fuser.getUid())){
                            usersList.add(chat.getSender());
                        }
                    }
                    readChats();
                }
            }
        });

        return view;


    }

    private void readChats(){
        mUsers = new ArrayList<>();

//        reference=FirebaseFirestore.getInstance();
//        reference.collection("App").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot document : task.getResult()){
//                        User user =document.toObject(User.class);
//                        for(String id : usersList){
//                            if(user.getId().equals(id)){
//                                if(mUsers.size()!=0){
////                                    for(User user1 : mUsers){
////                                        if(!user.getId().equals(user1.getId())){
////                                            mUsers.add(user);
////                                        }
////                                    }
//                                    for(int i =0; i<mUsers.size(); i++){
//                                        if(!user.getId().equals(mUsers.get(i).getId())){
//                                            mUsers.add(mUsers.get(i));
//                                        }
//                                    }
//                                }else{
//                                    mUsers.add(user);
//                                }
//                            }
//                        }
//                    }
//                    userAdapter = new UserAdapter(getContext(), mUsers, true);
//                    recyclerView.setAdapter(userAdapter);
//
//                }
//            }
//        });

    }


}
