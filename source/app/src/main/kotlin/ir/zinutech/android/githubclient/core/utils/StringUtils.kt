package ir.zinutech.android.githubclient.core.utils

import java.text.DateFormat
import java.util.Date
import java.util.Locale

object StringUtils {


  private var dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,
      Locale.getDefault())


  fun formatDate(date: Date): String {
    return dateFormat.format(date)
  }
}