package com.example.hakaton.ui.notifications


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import com.example.hakaton.R
import com.example.hakaton.databinding.FragmentNotificationsBinding
import androidx.navigation.fragment.findNavController
import com.example.hakaton.ui.SharedViewModel
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import com.example.hakaton.BoundingBox
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Добавление обработчика ввода текста в EditText Camera2
        val cameraEditText = root.findViewById<EditText>(R.id.Camera2)
        cameraEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d("EditTextInput", "Текст изменен: $s")
                sharedViewModel.setCamera2Text(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        // Добавление обработчика ввода текста в EditText opisanie
        val opisanieEditText = root.findViewById<EditText>(R.id.opisanie)
        opisanieEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d("EditTextInput", "Текст2 изменен: $s")
                sharedViewModel.setOpisanieText(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        // Получение Uri изображения из Bundle
        arguments?.getParcelable<Uri>("imageUri")?.let { uri ->
            binding.resultImageView.setImageURI(uri)
        }

        // Добавление обработчика нажатия на кнопку
        binding.zagruzit.setOnClickListener {
            // Переход на следующий фрагмент
            findNavController().navigate(R.id.action_navigation_notifications_to_fragment_resultat)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//class NotificationsFragment : Fragment() {
//
//    private var _binding: FragmentNotificationsBinding? = null
//    private val binding get() = _binding!!
//
//    private val sharedViewModel: SharedViewModel by activityViewModels()
//    private lateinit var prices: Interpreter
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        // Инициализация интерпретатора TensorFlow Lite
//        prices = initializePriceModel() // Инициализация модели
//
//        // Добавление обработчика ввода текста в EditText Camera2
//        val cameraEditText = root.findViewById<EditText>(R.id.Camera2)
//        cameraEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                Log.d("EditTextInput", "Текст изменен: $s")
//                sharedViewModel.setCamera2Text(s.toString())
//            }
//
//            override fun afterTextChanged(s: Editable) {}
//        })
//
//        // Добавление обработчика ввода текста в EditText opisanie
//        val opisanieEditText = root.findViewById<EditText>(R.id.opisanie)
//        opisanieEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                Log.d("EditTextInput", "Текст2 изменен: $s")
//                sharedViewModel.setOpisanieText(s.toString())
//            }
//
//            override fun afterTextChanged(s: Editable) {}
//        })
//
//        // Получение Uri изображения из Bundle
//        arguments?.getParcelable<Uri>("imageUri")?.let { uri ->
//            binding.resultImageView.setImageURI(uri)
//            processImageWithPriceModel(uri)
//        }
//
//        // Добавление обработчика нажатия на кнопку
//        binding.zagruzit.setOnClickListener {
//            // Переход на следующий фрагмент
//            findNavController().navigate(R.id.action_navigation_notifications_to_fragment_resultat)
//        }
//
//        return root
//    }
//
//    private fun initializePriceModel(): Interpreter {
//        val assetManager = context?.assets
//        val modelFile = assetManager?.open("price.tflite")
//        val byteBuffer = modelFile?.let {
//            val bytes = ByteArray(it.available())
//            it.read(bytes)
//            ByteBuffer.allocateDirect(bytes.size).apply {
//                order(ByteOrder.nativeOrder())
//                put(bytes)
//                position(0)
//            }
//        } ?: throw IllegalStateException("Price model file not found or could not be read.")
//        return Interpreter(byteBuffer)
//    }
//
//    private fun convertBitmapToByteBufferForPrice(bitmap: Bitmap): ByteBuffer {
//        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, true)
//        val byteBuffer = ByteBuffer.allocateDirect(4 * 640 * 640 * 3)
//        byteBuffer.order(ByteOrder.nativeOrder())
//        val intValues = IntArray(640 * 640)
//        scaledBitmap.getPixels(intValues, 0, scaledBitmap.width, 0, 0, scaledBitmap.width, scaledBitmap.height)
//        var pixel = 0
//        for (i in 0 until 640) {
//            for (j in 0 until 640) {
//                val `val` = intValues[pixel++]
//                byteBuffer.putFloat(((`val` shr 16 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//                byteBuffer.putFloat(((`val` shr 8 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//                byteBuffer.putFloat(((`val` and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//            }
//        }
//        byteBuffer.flip() // Перемещаем позицию буфера обратно в начало
//        return byteBuffer
//    }
//
//
//
//    private fun processImageWithPriceModel(imageUri: Uri) {
//        // Загрузка и преобразование изображения
//        val bitmap = BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(imageUri))
//        val byteBuffer = convertBitmapToByteBufferForPrice(bitmap)
//
//        // Выполнение модели
//        val outputBuffer = ByteBuffer.allocateDirect(4 * 640 * 640 * 3) // Размер выходных данных модели
//        outputBuffer.order(ByteOrder.nativeOrder())
//        prices.run(byteBuffer, outputBuffer)
//
//        // Обработка результатов
//        // Предполагая, что выходные данные модели представляют собой набор координат bounding boxes
//        // Вам нужно будет адаптировать эту часть в соответствии с форматом выходных данных вашей модели
//        val output = Array(1) { Array(640) { Array(640) { FloatArray(1) } } }
//        outputBuffer.rewind() // Перемещаем позицию буфера обратно в начало
//        for (i in 0 until 640) {
//            for (j in 0 until 640) {
//                // Пример обработки данных, вам нужно будет адаптировать эту часть
//                output[0][i][j] = FloatArray(1)
//                output[0][i][j][0] = outputBuffer.getFloat()
//            }
//        }
//        val boundingBoxes = extractBoundingBoxes(output) // Используем адаптированную функцию для извлечения bounding boxes
//        drawBoundingBoxes(binding.resultImageView, boundingBoxes)
//    }
//
//
//
//    private fun extractBoundingBoxes(output: Array<Array<Array<FloatArray>>>): List<BoundingBox> {
//        val boundingBoxes = mutableListOf<BoundingBox>()
//        // Пример обработки данных, вам нужно будет адаптировать эту часть
//        for (i in 0 until output[0].size) {
//            for (j in 0 until output[0][i].size) {
//                val left = output[0][i][j][0] // Пример извлечения координат bounding box
//                val top = output[0][i][j][0] // Пример извлечения координат bounding box
//                val right = output[0][i][j][0] // Пример извлечения координат bounding box
//                val bottom = output[0][i][j][0] // Пример извлечения координат bounding box
//                boundingBoxes.add(BoundingBox(left, top, right, bottom))
//            }
//        }
//        return boundingBoxes
//    }
//
//
//
//
//    private fun drawBoundingBoxes(imageView: ImageView, boundingBoxes: List<BoundingBox>) {
//        imageView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                // Удаляем слушатель, чтобы он не вызывался повторно
//                imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
//
//                // Теперь размеры imageView известны и положительны
//                val bitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
//                val canvas = Canvas(bitmap)
//                val paint = Paint().apply {
//                    color = Color.RED
//                    style = Paint.Style.STROKE
//                    strokeWidth = 4f
//                }
//
//                // Рисуем каждый боудинг бокс
//                for (box in boundingBoxes) {
//                    canvas.drawRect(box.left, box.top, box.right, box.bottom, paint)
//                }
//
//                imageView.setImageBitmap(bitmap)
//            }
//        })
//    }
//
//
//
//    companion object {
//        private const val IMAGE_MEAN = 128.0f
//        private const val IMAGE_STD = 128.0f
//    }
//
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}










