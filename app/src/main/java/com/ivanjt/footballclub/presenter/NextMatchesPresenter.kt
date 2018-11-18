package com.ivanjt.footballclub.presenter

import com.google.gson.Gson
import com.ivanjt.footballclub.api.SportDbAPI
import com.ivanjt.footballclub.model.EventResponse
import com.ivanjt.footballclub.utility.NetworkUtil
import com.ivanjt.footballclub.view.NextMatchesView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NextMatchesPresenter(private val view: NextMatchesView, private val gson: Gson) {
    fun getNextMatches(leagueId: String) {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(
                NetworkUtil.doRequest(SportDbAPI.getNextMatches(leagueId)),
                EventResponse::class.java
            )

            //Remove match if sport type of match is not soccer sport type
            data.events = data.events.filter { event -> event.sportType == "Soccer" }

            uiThread {
                view.hideLoading()
                view.showNextMatches(data.events)
            }
        }
    }
}