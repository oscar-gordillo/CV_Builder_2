package com.example.cvbuilder

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cvbuilder.databinding.ActivityMainBinding
import com.example.cvbuilder.db.*
import com.example.cvbuilder.network.APIClient
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var startforResultGalley: ActivityResultLauncher<Intent>
    var sImage:String=""

    private val RECORD_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startforResultGalley = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it!=null) {
                Toast.makeText(this, it.data?.data.toString(), Toast.LENGTH_LONG).show()
                Log.i("TEST", it.data?.data.toString())
                val uri = it.data?.data

                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG,50,stream);
                    val bytes = stream.toByteArray()
                    sImage= Base64.encodeToString(bytes, Base64.DEFAULT);
                    Log.i("TEST",sImage)
                } catch (e: Exception) {
                    // TODO: handle exception
                    e.printStackTrace()
                    Log.d("error", "onActivityResult: $e")
                }


            }
            else
                Toast.makeText(this,"Fail to retrieve", Toast.LENGTH_LONG).show()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)


        /*binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_skills, R.id.nav_experience
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        if (item.itemId==R.id.action_exportPDFImage){
            val i = Intent()
            // Activity Action for the intent : Pick an item from the data, returning what was selected.
            i.action = Intent.ACTION_PICK
            i.type = "image/*"
            startforResultGalley.launch(i)
        }
        if (item.itemId==R.id.action_exportPDF){


            /*try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                val bytes = stream.toByteArray()
                sImage= Base64.encodeToString(bytes, Base64.DEFAULT);
                Log.i("TEST",sImage)
            } catch (e: Exception) {
                // TODO: handle exception
                e.printStackTrace()
                Log.d("error", "onActivityResult: $e")
            }*/

            val scope = CoroutineScope(Job() + Dispatchers.Main)

            scope.launch {
                val spf= getSharedPreferences("myspf",Context.MODE_PRIVATE)
                val skills = CVBuilderDatabase(applicationContext).getSkillDao().getAllSkills()

                val educations = CVBuilderDatabase(applicationContext).getEducationDao().getAllEducation()
                //val educations= listOf<Education>(Education("MIU","Fairfield","IOWA","Compro","DEC-2024"))

                val experiences=CVBuilderDatabase(applicationContext).getExperienceDao().getAllExperience()

                /*val experiences= listOf<Experience>(
                    Experience("MIU","Fairfield","IOWA","JAN-2022","JUL-2022","Monitor","Monitor labs Verill Hall")
                    ,Experience("MIU","Fairfield","IOWA","JUL-2022","DEC-2022","Monitor","Monitor labs Library")
                )*/

                val certifications=CVBuilderDatabase(applicationContext).getCertificationDao().getAllCertification()
                /*val certification= listOf<Certification>(
                    Certification("AWS Developer","Amazon","JAN-2022","NA")
                    ,Certification("Java Developer","Oracle","AUG-2022","NA")
                )*/
                Log.i("TEST1",sImage)
                val cv=CV(spf.getString("name","")!!
                    ,spf.getString("phone","")!!
                    ,spf.getString("email","")!!
                    ,spf.getString("title","")!!
                    ,spf.getString("description","")!!
                    ,skills
                    ,experiences
                    ,educations
                    ,certifications
                    ,spf.getString("template","")!!
                    ,sImage)
                val gson= Gson()
                val value=gson.toJson(cv)
                Log.i("TEST2",value)
                var call = APIClient.apiInterface().postCV(cv)

                call.enqueue(object : Callback<String> { // Make a call to hit the server
                    // hit if you receive the response--> which is to retrieve the List of ImageData
                    override fun onResponse(call: Call<String?>?, response: Response<String?>?) {
                        if( response!!.isSuccessful){ // Check using non null !! operator
                            // The deserialized response body of a successful response ie List<Animals>
                            Toast.makeText(applicationContext,"Downloading PDF...", Toast.LENGTH_LONG).show()
                            Log.i("TEST",response.body()!!.toString())

                            val spf= getSharedPreferences("myspf", Context.MODE_PRIVATE)
                            val spe=spf.edit()
                            spe.putString("cloud_id",response.body()!!.toString())
                            spe.apply()

                            /*val format =
                                "https://drive.google.com/viewerng/viewer?embedded=true&url=%s"
                            val fullPath: String =
                                java.lang.String.format(Locale.ENGLISH, format, "https://ged21c9a6c3b8b0-free1.adb.us-phoenix-1.oraclecloudapps.com/ords/r/mdp/cv-builder-app/test?p5_id="+response.body()!!.toString())
*/
                            val url = "https://ged21c9a6c3b8b0-free1.adb.us-phoenix-1.oraclecloudapps.com/ords/r/mdp/cv-builder-app/test?p5_id="+response.body()!!.toString()
                            val i = Intent(Intent.ACTION_VIEW,Uri.parse(url))
                            startActivity(i)
                        }
                    }
                    // Unable to get a response
                    override fun onFailure(call: Call<String?>?, t: Throwable?) {
                        // The localized description of this throwable, like getErrorMessage from throwable
                        Toast.makeText(applicationContext,"An Error Occured  ${t?.localizedMessage}", Toast.LENGTH_LONG).show()

                    }

                })

            }



            /*val skills= listOf<Skill>(Skill("language","Java",60)
                ,Skill("tool","VSCode",80)
                ,Skill("language","SQL",100)
                ,Skill("language","C++",50)
                ,Skill("language","PLSQL",80)
            )*/


        }
        return super.onOptionsItemSelected(item)
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            RECORD_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults : IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.
                    PERMISSION_GRANTED) {
                    Toast.makeText(this,"Permission has been denied by user",Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this,"Permission has been granted by the user",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}