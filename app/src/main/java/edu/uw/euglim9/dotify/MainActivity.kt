package edu.uw.euglim9.dotify

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import edu.uw.euglim9.dotify.databinding.ActivityMainBinding
import kotlin.random.Random
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var randomNumber = Random.nextInt(1000, 10000)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.tvPlays.text = "$randomNumber plays"

        binding.btnChangeUser.setOnClickListener {
            binding.tvUsername.visibility = View.INVISIBLE
            binding.etUsername.visibility = View.VISIBLE
            binding.btnChangeUser.text = "APPLY"
            
            binding.btnChangeUser.setOnClickListener {
                binding.tvUsername.text = binding.etUsername.text.toString()
                binding.btnChangeUser.text = "CHANGE USER"
                binding.etUsername.visibility = View.INVISIBLE
                binding.tvUsername.visibility = View.VISIBLE
            }
        }
    }

    fun incrementPlays(view: View) {
        randomNumber += 1;
        binding.tvPlays.text = "$randomNumber plays"
    }

    fun previousClicked(view: View) {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
    }

    fun nextClicked(view: View) {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
    }
}