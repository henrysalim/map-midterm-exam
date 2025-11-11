package com.example.midtermexam.supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


class Supabase {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://xyzcompany.supabase.co",
        supabaseKey = "${BuildConfig.SUPABASE_ANON_KEY}"
    ) {
        install(Auth)
        install(Postgrest)
        //install other modules
    }
}